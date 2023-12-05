package store.data;

import fileio.input.UserInput;

import java.util.ArrayList;
import java.util.List;

public class InitUserList {
    private List<StoreUsers> usersList = new ArrayList<>();

    public final List<StoreUsers> getUsersList() {
        return usersList;
    }

    /**
     *
     * @param libraryUsers
     * @return
     */
    public List<StoreUsers> init(final ArrayList<UserInput> libraryUsers) {
        for (UserInput currLibraryUser : libraryUsers) {
            StoreUsers newUser = new StoreUsers();
            newUser.setUsername(currLibraryUser.getUsername());
            newUser.setAge(currLibraryUser.getAge());
            newUser.setCity(currLibraryUser.getCity());
            usersList.add(newUser);
        }
        return this.usersList;
    }
}
