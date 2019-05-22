package my.vaadin.app;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * An in memory dummy "database" for the example purposes. In a typical Java app
 * this class would be replaced by e.g. EJB or a Spring based service class.
 * <p>
 * In demos/tutorials/examples, get a reference to this service class with
 * {@link MeetingService#getInstance()}.
 */
public class MeetingService {

	private static MeetingService instance;
	private static final Logger LOGGER = Logger.getLogger(MeetingService.class.getName());

	private final HashMap<Long, Meeting> meetings = new HashMap<>();
	private long nextId = 0;

	private MeetingService() {
	}

	/**
	 * @return a reference to an example facade for Meeting objects.
	 */
	public static MeetingService getInstance() {
		if (instance == null) {
			instance = new MeetingService();
		}
		return instance;
	}

	/**
	 * @return all available Meeting objects for the given customer.
	 */

	public synchronized List<Meeting> findAll(Long customerId) {
		ArrayList<Meeting> arrayList = new ArrayList<>();

		for (Meeting meeting : meetings.values()) {
			try {
				if (customerId == meeting.getCustomerId()) {
					arrayList.add(meeting.clone());
				}
			} catch (CloneNotSupportedException ex) {
				Logger.getLogger(CustomerService.class.getName()).log(Level.SEVERE, null, ex);
			}
		}

		Collections.sort(arrayList, new Comparator<Meeting>() {

			@Override
			public int compare(Meeting o1, Meeting o2) {
				return (int) (o2.getId() - o1.getId());
			}
		});
		return arrayList;
	}

	/**
	 * Deletes a Meeting from a system
	 *
	 * @param value
	 *            the Meeting to be deleted
	 */
	public synchronized void delete(Meeting value) {
		meetings.remove(value.getId());
	}

	/**
	 * Persists or updates Meeting in the system. Also assigns an identifier
	 * for new Meeting instances.
	 *
	 * @param entry
	 */
	public synchronized void save(Meeting entry) {
		if (entry == null) {
			LOGGER.log(Level.SEVERE,
					"Meeting is null. Are you sure you have connected your form to the application as described in tutorial chapter 7?");
			return;
		}
		if (entry.getId() == null) {
			entry.setId(nextId++);
		}
		try {
			entry = (Meeting) entry.clone();
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
		meetings.put(entry.getId(), entry);
	}
}