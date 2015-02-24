package my.first.step.common;

import java.util.Date;
import java.util.List;

import my.first.step.persistence.HibernateUtil;

import org.hibernate.Query;
import org.hibernate.Session;

/**
 * @author JBD
 *
 *         Cette classe permet d'effectuer quelques traitements simples sur une
 *         classe stock qui sera le reflet de la table stock située en base de
 *         données.
 * 
 *         Pour que l'exemple fonctionne, on vérifiera plusieurs choses :
 * 
 *         -Il existe une base de donnée nommée "hibernate".
 * 
 *         -Il existe un utilisateur nommé "root" et dont le mot de passe est
 *         "root". Si cela ne correspond pas, il est possible de modifier les
 *         valeurs prédéfinies dans le fichier hibernate.cfg.xml
 * 
 *         -Il existe une table stock dans la base de données (celle-ci doit
 *         être créée grâce au fichier stock.sql)
 *
 */
public class MyFirstStepWithHibernate {

	private Session session = null;

	public MyFirstStepWithHibernate() {

	}

	/**
	 * Cette méthode permet de réaliser l'enregistrement d'un stock en base de
	 * données en utilisant hibernate
	 */
	public void insert() {
		/*
		 * Dans un premier lieu, on crée une transaction. Tant que celle-ci
		 * n'est pas validée grâce à la méthode commit, si une exception
		 * intervient, toutes les modifications effectuées dans cette
		 * transaction seront annulées
		 */
		session.beginTransaction();

		/*
		 * Comme on veux créer un stock en base de données, on crée un nouvel
		 * objet stock, auquel on affecte les données désirées.
		 */
		Stock stock = new Stock();

		stock.setStockCode("4715");
		stock.setStockName("test");

		/*
		 * Puis, grâce à la méthode save, on indique à hibernate qu'il doit
		 * rendre persistant cette données. Après avoir utilisé cette méthode,
		 * l'objet stock aura son équivalent en base de données.
		 * 
		 * Cette méthode va donc générer une requête sql d'insertion qui sera
		 * affichée dans la console lors de son execution.
		 */

		DailyRecord dailyRecord = new DailyRecord();

		dailyRecord.setDate(new Date());
		dailyRecord.setVolume(12L);
		dailyRecord.setStock(stock);

		session.save(stock);

		/*
		 * Une fois les traitement effectués, on valide la transaction afin que
		 * les requêtes executées soient validées.
		 */
		session.getTransaction().commit();
	}

	/**
	 * Cette méthode permet de récupérer sous la forme d'une liste d'objet Stock
	 * les enregistrements présents en base de données.
	 */
	public List<Stock> listStock() {
		System.out.println("Liste des stocks : ");
		session.beginTransaction();
		/*
		 * Cette méthode permet de renvoyer toutes les lignes de la table stock,
		 * puis de les mapper afin d'obtenir des objets Stock
		 */
		List<Stock> list = session.createCriteria(Stock.class).list();
		for (Stock s : list) {
			/*
			 * Arrivé à ce point, Hibernate à chargé les stocks, néanmoins,
			 * comme le lien one-to-many a été configuré comme lazy (cf:
			 * DailyRecord.hbm.xml et Stock.hbm.xml), les enregistrements
			 * journaliers n'ont pas été chargés. Cela est bien visible
			 * lorsqu'on essaie d'y accéder, car une nouvelle requête est
			 * exectuée (voir console).
			 * 
			 * Ainsi, on effectue une première requête pour obtenir les stocks,
			 * mais une requête de plus est effectuée lorsqu'on accède pour la
			 * première fois à un objet auquel est lié l'objet de base.
			 */
			System.out.println(s);
			System.out.println(s.getDailyRecords());
		}
		session.getTransaction().commit();
		return list;
	}

	/**
	 * Cette méthode retourne une liste de stock dont le nom est "test".
	 * 
	 * @return
	 */
	public List<Stock> getTestStock() {
		session.beginTransaction();
		/*
		 * Jusqu'à maintenant, nous avons vu comment obtenir toutes les
		 * instances d'un type. Dans le cas où on rechercherais des données
		 * particulière, on utilise des requêtes hql. Le hql ressemble fortement
		 * à du sql à ceci près qu'il utilise les objets au lieu des tables.
		 * Comme on ne peut récuperer que des objets, il est inutile d'indiquer
		 * "select *" car l'objet entier doit être retourné. Puis, dans le
		 * where, on peut effectuer des tests sur les attributs de cet objet :
		 * s.stockName appelle en fait s.getStockName(), il est donc important
		 * de respecter la casse des getters et setters.
		 */
		String requete = "select s from Stock s where s.stockName = :stockName";
		Query query = session.createQuery(requete);
		/*
		 * Comme vous l'aurez remarqué, la requête contient :stockName, qui
		 * correspond à un paramètre. Il est possible de le remplacer grâce à
		 * setString (et ainsi d'éviter l'injection, comme en jdbc).
		 */
		query.setString("stockName", "test");
		/*
		 * La requête retourne ensuite les données directement sous forme d'une
		 * liste d'objet.
		 */
		List<Stock> list = query.list();
		session.getTransaction().commit();
		return list;
	}

	/**
	 * Cette methode permet de lister les enregistrements journaliers existant
	 * en base de données et de les restituer sous forme d'objet
	 * 
	 * @return
	 */
	public List<DailyRecord> listDailyRecord() {
		System.out.println("Liste des enregistrements quotidiens : ");
		session.beginTransaction();
		/*
		 * Cette méthode permet de renvoyer toutes les lignes de la table
		 * daily_record, puis de les mapper afin d'obtenir des objets Stock
		 */
		List<DailyRecord> list = session.createCriteria(DailyRecord.class).list();
		for (DailyRecord d : list) {
			System.out.println(d);
		}
		session.getTransaction().commit();
		return list;
	}

	/**
	 * Cette methode permet de renvoyer une liste de stock pour lesquels il
	 * existe un enregistrement journalier correspondant à aujourd'hui
	 * 
	 * @return
	 */
	public List<Stock> listStockWithDailyRecord() {
		session.beginTransaction();
		String requete = "select s from Stock s join s.dailyRecords as d where d is not empty and d.date = :today";
		Query query = session.createQuery(requete);
		/*
		 * Comme vous l'aurez remarqué, la requête contient :stockName, qui
		 * correspond à un paramètre. Il est possible de le remplacer grâce à
		 * setString (et ainsi d'éviter l'injection, comme en jdbc).
		 */
		query.setDate("today", new Date());
		/*
		 * La requête retourne ensuite les données directement sous forme d'une
		 * liste d'objet. On remarquera que, malgré le fait que l'on execute une
		 * requête effectuant des tests sur dailyRecords, la liste n'est pas
		 * chargé et une autre requête sera tout de même executée.
		 */
		List<Stock> list = query.list();
		for (Stock s : list) {
			System.out.println(s);
			System.out.println(s.getDailyRecords());
		}
		session.getTransaction().commit();
		return list;
	}

	/**
	 * Cette méthode permet, s'il n'en existe pas encore, d'ouvrir une session
	 * hibernate
	 */
	public void openSession() {
		if (session == null) {
			session = HibernateUtil.getSessionFactory().openSession();
		}
	}

	/**
	 * Cette méthode permet, s'il en existe une, de terminer la session
	 * hibernate
	 */
	public void closeSession() {
		session.close();
		session = null;
	}

	public static void main(String[] args) {
		MyFirstStepWithHibernate hibernate = new MyFirstStepWithHibernate();
		hibernate.openSession();
		hibernate.insert();
		hibernate.listStock();
		hibernate.listDailyRecord();
		hibernate.listStockWithDailyRecord();
		hibernate.closeSession();
		System.exit(0);
	}
}
