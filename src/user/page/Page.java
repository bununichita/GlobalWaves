package user.page;

import store.data.StoreNormalUsers;

public interface Page {
    public String print(StoreNormalUsers user);
    public String getType(StoreNormalUsers user);
}
