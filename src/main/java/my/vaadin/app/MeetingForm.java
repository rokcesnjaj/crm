package my.vaadin.app;

import com.vaadin.data.Binder;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;

public class MeetingForm extends FormLayout {

    private TextField location = new TextField("Location");
    private Long customerId;
    private DateTimeField startTime = new DateTimeField("Start time");
    private DateTimeField endTime = new DateTimeField("End time");
    private Button save = new Button("Save");
    private Button delete = new Button("Delete");

    private MeetingService service = MeetingService.getInstance();
    private Meeting meeting;
    private MyUI myUI;
    private Binder<Meeting> binder = new Binder<>(Meeting.class);

    public MeetingForm(MyUI myUI) {
        this.myUI = myUI;

        setSizeUndefined();
        HorizontalLayout buttons = new HorizontalLayout(save, delete);
        addComponents(location, startTime, endTime, buttons);

        save.setStyleName(ValoTheme.BUTTON_PRIMARY);
        save.setClickShortcut(KeyCode.ENTER);
        startTime.setDateFormat("dd.MM.yyyy HH:mm:ss");
        endTime.setDateFormat("dd.MM.yyyy HH:mm:ss");

        binder.bindInstanceFields(this);

        save.addClickListener(e -> this.save());
        delete.addClickListener(e -> this.delete());
    }

    public void setMeeting(Meeting meeting) {
        this.meeting = meeting;
        binder.setBean(meeting);

        // Show delete button for only customers already in the database
        delete.setVisible(meeting.isPersisted());
        setVisible(true);
        location.selectAll();
    }

    private void delete() {
        Long customerId = meeting.getCustomerId();
        service.delete(meeting);
        myUI.updateMeetingList(customerId);
        setVisible(false);
    }

    private void save() {
        service.save(meeting);
        myUI.updateMeetingList(meeting.getCustomerId());
        setVisible(false);
    }

}
