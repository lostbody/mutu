package gr.aueb.cf.mutu.dao;

import java.util.Set;

public interface IUserInterestDao {
    void setUserInterests(long id, Set<Long> interestIds);
}
