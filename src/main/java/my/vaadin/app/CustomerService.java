package my.vaadin.app;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * An in memory dummy "database" for the example purposes. In a typical Java app
 * this class would be replaced by e.g. EJB or a Spring based service class.
 * <p>
 * In demos/tutorials/examples, get a reference to this service class with
 * {@link CustomerService#getInstance()}.
 */
public class CustomerService {

	private static CustomerService instance;
	private static final Logger LOGGER = Logger.getLogger(CustomerService.class.getName());

	private final HashMap<Long, Customer> contacts = new HashMap<>();
	private long nextId = 0;

	private CustomerService() {
	}

	/**
	 * @return a reference to an example facade for Customer objects.
	 */
	public static CustomerService getInstance() {
		if (instance == null) {
			instance = new CustomerService();
			instance.ensureTestData();
		}
		return instance;
	}

	/**
	 * @return all available Customer objects.
	 */
	public synchronized List<Customer> findAll() {
		return findAll(null);
	}

	/**
	 * Finds all Customer's that match given filter.
	 *
	 * @param stringFilter
	 *            filter that returned objects should match or null/empty string
	 *            if all objects should be returned.
	 * @return list a Customer objects
	 */
	public synchronized List<Customer> findAll(String stringFilter) {
		ArrayList<Customer> arrayList = new ArrayList<>();
		for (Customer contact : contacts.values()) {
			try {
				boolean passesFilter = (stringFilter == null || stringFilter.isEmpty())
						|| contact.toString().toLowerCase().contains(stringFilter.toLowerCase());
				if (passesFilter) {
					arrayList.add(contact.clone());
				}
			} catch (CloneNotSupportedException ex) {
				Logger.getLogger(CustomerService.class.getName()).log(Level.SEVERE, null, ex);
			}
		}
		Collections.sort(arrayList, new Comparator<Customer>() {

			@Override
			public int compare(Customer o1, Customer o2) {
				return (int) (o2.getId() - o1.getId());
			}
		});
		return arrayList;
	}

	/**
	 * Finds all Customer's that match given filter and limits the resultset.
	 *
	 * @param stringFilter
	 *            filter that returned objects should match or null/empty string
	 *            if all objects should be returned.
	 * @param start
	 *            the index of first result
	 * @param maxresults
	 *            maximum result count
	 * @return list a Customer objects
	 */
	public synchronized List<Customer> findAll(String stringFilter, int start, int maxresults) {
		ArrayList<Customer> arrayList = new ArrayList<>();
		for (Customer contact : contacts.values()) {
			try {
				boolean passesFilter = (stringFilter == null || stringFilter.isEmpty())
						|| contact.toString().toLowerCase().contains(stringFilter.toLowerCase());
				if (passesFilter) {
					arrayList.add(contact.clone());
				}
			} catch (CloneNotSupportedException ex) {
				Logger.getLogger(CustomerService.class.getName()).log(Level.SEVERE, null, ex);
			}
		}
		Collections.sort(arrayList, new Comparator<Customer>() {

			@Override
			public int compare(Customer o1, Customer o2) {
				return (int) (o2.getId() - o1.getId());
			}
		});
		int end = start + maxresults;
		if (end > arrayList.size()) {
			end = arrayList.size();
		}
		return arrayList.subList(start, end);
	}

	/**
	 * @return the amount of all customers in the system
	 */
	public synchronized long count() {
		return contacts.size();
	}

	/**
	 * Deletes a customer from a system
	 *
	 * @param value
	 *            the Customer to be deleted
	 */
	public synchronized void delete(Customer value) {
		contacts.remove(value.getId());
	}

	/**
	 * Persists or updates customer in the system. Also assigns an identifier
	 * for new Customer instances.
	 *
	 * @param entry
	 */
	public synchronized void save(Customer entry) {
		if (entry == null) {
			LOGGER.log(Level.SEVERE,
					"Customer is null. Are you sure you have connected your form to the application as described in tutorial chapter 7?");
			return;
		}
		if (entry.getId() == null) {
			entry.setId(nextId++);
		}
		try {
			entry = (Customer) entry.clone();
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
		contacts.put(entry.getId(), entry);
	}

	/**
	 * Sample data generation
	 */
	public void ensureTestData() {
		if (findAll().isEmpty()) {
			final String[] customers = new String[] {
				"Gabrielle Patel 'P.O. Box 701, 3347 Massa. Street' Saint-Vincent 74978-808",
				"Brian Robinson '1545 Gravida. Rd.' Talcahuano 65005",
				"Eduardo Haugen 'P.O. Box 452, 1063 Risus St.' Melrose 8569",
				"Koen Johansen '152-621 Rutrum Rd.' Plauen 94-173",
				"Alejandro Macdonald '1192 Adipiscing Road' Baarle-Hertog 29192",
				"Angel Karlsson 'P.O. Box 398, 8977 Penatibus Av.' Bonlez U6B 1NT",
				"Yahir Gustavsson '615-2475 Dolor. Road' Maple Creek 27817",
				"Haiden Svensson 'Ap #414-666 Mauris Street' Viggianello 774",
				"Emily Stewart '235 Morbi Avenue' Bradford 60308",
				"Corinne Davis '5934 Imperdiet St.' Etobicoke 53-747",
				"Ryann Davis '629-5812 Adipiscing Av.' Lamorteau E1W 7H6",
				"Yurem Jackson '360-7418 Donec Rd.' Salcito 21687-445",
				"Kelly Gustavsson 'Ap #487-6141 Nunc St.' Hattiesburg 43336",
				"Eileen Walker '9949 Curabitur St.' Mussy-la-Ville 82060",
				"Kately Martin '101-9139 Diam Road' Lutsel K'e 8543",
				"Palestin Carlsson '141-6645 Phasellus Ave' Idar-Oberstei 647522",
				"Quinn Hansson '404-8949 Primis Av.' Calgary P7B 6Z8",
				"Makena Smith 'Ap #100-7117 Morbi Av.' Hull 487036",
				"Danielle Watson 'P.O. Box 522, 6961 Sit Avenue' Tailles 240747",
				"Leland Harris 'P.O. Box 427, 4757 Volutpat. Rd.' Macklin 3373",
				"Gunner Karlsen 'P.O. Box 321, 6674 Aliquet, Ave' Secunderabad 190230",
				"Jamar Olsson '598 Consectetuer, Street' Turnhout 9601 AZ",
				"Lara Martin '1959 Sem St.' Kenosha HH9Z 5RD",
				"Ann Andersson '7681 Morbi Road' Fermont 3947 NR",
				"Remington Andersson '155-1243 Etiam Ave' Santo Domingo 60-111",
				"Rene Carlsson '5726 Interdum Street' Firenze 5383",
				"Elvis Olsen '992-5397 Sed St.' Gatineau 59636",
				"Solomon Olsen '1054 Tincidunt Rd.' Offenbach am Main 91768",
				"Jaydan Jackson 'Ap #611-6171 Commodo Avenue' Berwick H5A 2OW",
				"Bernard Nilsen 'Ap #420-6116 A, St.' Sennariolo 9761"
			};
			Random r = new Random(0);
			for (String customer : customers) {
				String[] split = customer.split("'");
				String[] splitName = split[0].split(" ");
				String[] splitCity = split[2].split(" ");
				int daysOld = 0 - r.nextInt(365 * 15 + 365 * 60);
				Customer c = new Customer();
				c.setFirstName(splitName[0]);
				c.setLastName(splitName[1]);
				c.setBirthDate(LocalDate.now().plusDays(daysOld));
				c.setEmail(splitName[0].toLowerCase() + "@" + splitName[1].toLowerCase() + ".com");
				c.setAddress(split[1].replace("'", ""));
				c.setCity(splitCity[1]);
				c.setPostalCode(splitCity[2]);
				save(c);
			}
		}
	}
}