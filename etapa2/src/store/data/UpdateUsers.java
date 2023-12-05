package store.data;

import java.util.List;

public class UpdateUsers {
    private List<StoreUsers> usersList;
    public UpdateUsers(final List<StoreUsers> usersList) {
        this.usersList = usersList;
    }

    public final List<StoreUsers> getUsersList() {
        return usersList;
    }

    public final void setUsersList(final List<StoreUsers> usersList) {
        this.usersList = usersList;
    }

}
