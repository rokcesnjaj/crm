package my.vaadin.app;

import java.util.List;

import javax.servlet.annotation.WebServlet;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.shared.data.sort.SortDirection;
import com.vaadin.shared.ui.ValueChangeMode;
import com.vaadin.ui.Button;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

/**
 * This UI is the application entry point. A UI may either represent a browser window 
 * (or tab) or some part of a html page where a Vaadin application is embedded.
 * <p>
 * The UI is initialized using {@link #init(VaadinRequest)}. This method is intended to be 
 * overridden to add component to the user interface and initialize non-component functionality.
 */
@Theme("mytheme")
public class MyUI extends UI {

    private CustomerService customerService = CustomerService.getInstance();
    private MeetingService meetingService = MeetingService.getInstance();
    private Grid<Customer> customerGrid = new Grid<>(Customer.class);
    private Grid<Meeting> meetingGrid = new Grid<>(Meeting.class);
    private TextField customerFilterText = new TextField();
    private CustomerForm customerForm = new CustomerForm(this);
    private MeetingForm meetingForm = new MeetingForm(this);
    private Long customerId;

    @Override
    protected void init(VaadinRequest vaadinRequest) {
        final VerticalLayout layout = new VerticalLayout();

        /**** customer ****/
        /** filter **/

        customerFilterText.setPlaceholder("filter by name...");
        customerFilterText.addValueChangeListener(e -> updateCustomerList());
        customerFilterText.setValueChangeMode(ValueChangeMode.LAZY);

        /** clear customer filter button **/
        Button clearCustomerFilterTextBtn = new Button(FontAwesome.TIMES);
        clearCustomerFilterTextBtn.setDescription("Clear the current filter");
        clearCustomerFilterTextBtn.addClickListener(e -> customerFilterText.clear());

        CssLayout customerFiltering = new CssLayout();
        customerFiltering.addComponents(customerFilterText, clearCustomerFilterTextBtn);
        customerFiltering.setStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);

        /** add new customer button **/
        Button addCustomerBtn = new Button("Add new customer");
        addCustomerBtn.addClickListener(e -> {
            customerGrid.asSingleSelect().clear();
            customerForm.setCustomer(new Customer());
        });

        HorizontalLayout customerToolbarLayout = new HorizontalLayout(customerFiltering, addCustomerBtn);

        customerGrid.setColumns("firstName", "lastName", "birthDate", "email");

        HorizontalLayout mainCustomerLayout = new HorizontalLayout(customerGrid, customerForm);
        mainCustomerLayout.setSizeFull();
        customerGrid.setSizeFull();
        mainCustomerLayout.setExpandRatio(customerGrid, 1);

        /**** meeting ****/
        /** add new meeting button **/
        Button addMeetingBtn = new Button("Add new meeting");
        addMeetingBtn.addClickListener(e -> {
            Meeting newMeeting = new Meeting();
            newMeeting.setCustomerId(customerId);
            meetingGrid.asSingleSelect().clear();
            meetingForm.setMeeting(newMeeting);
        });

        meetingGrid.setColumns("location", "startTime", "endTime");
        meetingGrid.sort("startTime", SortDirection.DESCENDING);

        HorizontalLayout mainMeetingLayout = new HorizontalLayout(meetingGrid, meetingForm);
        mainMeetingLayout.setSizeFull();
        meetingGrid.setSizeFull();
        mainMeetingLayout.setExpandRatio(meetingGrid, 1);

        layout.addComponents(customerToolbarLayout, mainCustomerLayout, addMeetingBtn, mainMeetingLayout);

        // fetch list of Customers from customerService and assign it to Grid
        updateCustomerList();

        setContent(layout);

        customerForm.setVisible(false);
        meetingForm.setVisible(false);
        addMeetingBtn.setVisible(false);
        meetingGrid.setVisible(false);

        customerGrid.asSingleSelect().addValueChangeListener(event -> {
            if (event.getValue() == null) {
                customerForm.setVisible(false);
                meetingForm.setVisible(false);
                addMeetingBtn.setVisible(false);
                meetingGrid.setVisible(false);
            } else {
                customerId = event.getValue().getId();
                customerForm.setCustomer(event.getValue());
                updateMeetingList(event.getValue().getId());
                addMeetingBtn.setVisible(true);
                meetingGrid.setVisible(true);
            }
        });

        meetingGrid.asSingleSelect().addValueChangeListener(event -> {
            if (event.getValue() == null) {
                meetingForm.setVisible(false);
            } else {
                meetingForm.setMeeting(event.getValue());
            }
        });
    }

    public void updateCustomerList() {
        List<Customer> customers = customerService.findAll(customerFilterText.getValue());
        customerGrid.setItems(customers);
    }

    public void updateMeetingList(Long customerId) {
        List<Meeting> meetings = meetingService.findAll(customerId);
        meetingGrid.setItems(meetings);
    }

    @WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = MyUI.class, productionMode = false)
    public static class MyUIServlet extends VaadinServlet {
    }
}
