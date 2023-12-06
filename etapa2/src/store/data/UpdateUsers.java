package store.data;

import java.util.List;

public class UpdateUsers {
    private List<StoreNormalUsers> usersList;
    public UpdateUsers(final List<StoreNormalUsers> usersList) {
        this.usersList = usersList;
    }

    public final List<StoreNormalUsers> getUsersList() {
        return usersList;
    }

    public final void setUsersList(final List<StoreNormalUsers> usersList) {
        this.usersList = usersList;
    }

}
