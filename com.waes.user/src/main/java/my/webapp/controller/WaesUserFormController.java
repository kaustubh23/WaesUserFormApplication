package my.webapp.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zul.Messagebox;

import my.webapp.model.User;

public class WaesUserFormController extends SelectorComposer<Component> {

	private static final long serialVersionUID = 1L;
	private final String MESSAGE = "Submission failed: Possible Reasons:\n- Input All Fields. \n- Fields 'User Name' and 'Post Title' can not have special characters(%$&* etc) and numbers";
	private final String POSTTITLE = "Post Title";
	private final String USERNAME = "User Name";
	private List<User> allUserData = new ArrayList<User>();
	List<User> existingList = new ArrayList<User>();
	User user = new User();
	

	private boolean visible;

	// Init call from the zul file to the controller
	@Init
	public void init() {
		setVisible(false);

	}

	@Command
	@NotifyChange({ "onClear" })
	public void onClear() {
		{
			if (getUser().getSearchByuserName() != null) {
				setAllUserData(existingList);

			}
		}

	}

	// Search Logic by UserName
	@Command
	@NotifyChange({ "onSearch" })
	public void onSearch() {
		List<User> userList = new ArrayList<User>();
		if (getUser().getSearchByuserName() != null && !getUser().getSearchByuserName().isEmpty()&& !getUser().getSearchByuserName().equalsIgnoreCase(USERNAME)) {
			for (User user : getAllUserData()) {
				if (getUser().getSearchByuserName() != null) {
					if (getUser().getSearchByuserName().equalsIgnoreCase(user.getUserName())) {
						userList.add(user);
					}
				} else {
					setAllUserData(existingList);
				}
			}
			if (userList != null && !userList.isEmpty()) {
				setAllUserData(userList);
			} else {
				setAllUserData(new ArrayList<User>());
			}
		}

	}

	// Adding form values to the list

	@Command
	@NotifyChange({ "allUserData" })
	public void onAddNew() {
		setAllUserData(existingList);

		String regex = "^[a-zA-Z_ ]*$";

		Pattern pattern = Pattern.compile(regex);

		if (getUser().getUserName() != null && getUser().getPostTitle() != null) {
			Matcher matcher = pattern.matcher(getUser().getUserName());
			Matcher matcher2 = pattern.matcher(getUser().getPostTitle());

			if (matcher.matches() && matcher2.matches() && user.getLikes() <= 999999999 && user.getViews() <= 999999999
					&& !getUser().getUserName().equalsIgnoreCase(USERNAME)
					&& !getUser().getPostTitle().equalsIgnoreCase(POSTTITLE)) {

				setVisible(true);
				List<User> userList = new ArrayList<User>();
				List<User> userListUpdate = new ArrayList<User>();
				User user = new User();

				user.setActive(1);
				user.setId(getAllUserData().size() + 1);
				user.setLikes(getUser().getLikes());
				user.setUserName(getUser().getUserName());
				user.setPostTitle(getUser().getPostTitle());
				user.setViews(getUser().getViews());
				LocalDate localDate = LocalDate.now();
				user.setCreatedAt(localDate);

				userList.add(user);
                clear();
				if (getAllUserData() != null && !getAllUserData().isEmpty()) {
					userListUpdate = getAllUserData();
					userListUpdate.addAll(getAllUserData().size(), userList);
					for (User user1 : userListUpdate) {
						user1.setActive(0);
					}
					userListUpdate.get(userListUpdate.size() - 1).setActive(1);
					setAllUserData(userListUpdate);
				} else {
					setAllUserData(userList);
				}
				getUser().setSelect(1);
				existingList = getAllUserData();

			} else {
				Messagebox.show(MESSAGE);
			}
		} else {
			Messagebox.show(MESSAGE);

		}

	}
public void clear(){
	getUser().setViews(0);
	getUser().setLikes(0);
}
	// setter getter of local variables and models. The values are bind with
	// front end screen variables to respective properties.
	public boolean isVisible() {
		return visible;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
		BindUtils.postNotifyChange(null, null, this, "visible");

	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
		BindUtils.postNotifyChange(null, null, this, "user");
	}

	public List<User> getAllUserData() {
		return allUserData;
	}

	public void setAllUserData(List<User> allUserData) {
		this.allUserData = allUserData;

		BindUtils.postNotifyChange(null, null, this, "allUserData");
	}

	// Onclick event of column tab one of below event launch and perform sorting
	// based on selection

	@Command
	@NotifyChange({ "allUserData" })
	public void sortByID() {

		sort(1);
	}

	@Command
	@NotifyChange({ "allUserData" })
	public void sortByUserName() {

		sort(2);
	}

	@Command
	@NotifyChange({ "allUserData" })
	public void sortByProfileTitle() {

		sort(3);
	}

	@Command
	@NotifyChange({ "allUserData" })
	public void sortByViews() {

		sort(4);
	}

	@Command
	@NotifyChange({ "allUserData" })
	public void sortByLikes() {

		sort(5);
	}

	@Command
	@NotifyChange({ "allUserData" })
	public void sortByCreateAt() {

		sort(6);
	}

	// Sorting Logic
	public void sort(int type) {
		if (getUser().isSwtch()) {
			getUser().setSwtch(false);
		} else {
			getUser().setSwtch(true);
		}
		Collections.sort(getAllUserData(), (o1, o2) -> {
			switch (type) {
			case 1: // Compare Title
				return new Integer(o1.getId()).compareTo(o2.getId()) * (getUser().isSwtch() ? 1 : -1);
			case 2: // Compare First Name
				return o1.getUserName().compareTo(o2.getUserName()) * (getUser().isSwtch() ? 1 : -1);
			case 3: // Compare Last Name
				return o1.getPostTitle().compareTo(o2.getPostTitle()) * (getUser().isSwtch() ? 1 : -1);
			case 4: // Compare Extension
				return new Integer(o1.getViews()).compareTo(o2.getViews()) * (getUser().isSwtch() ? 1 : -1);
			case 5: // Full Name
				return new Integer(o1.getLikes()).compareTo(o2.getLikes()) * (getUser().isSwtch() ? 1 : -1);
			case 6: // Full Name
				return o1.getCreatedAt().compareTo(o2.getCreatedAt()) * (getUser().isSwtch() ? 1 : -1);
			default: // Full Name
				return new Integer(o1.getLikes()).compareTo(o2.getLikes()) * (getUser().isSwtch() ? -1 : 1);

			}

		});

	}

}
