package gr.aueb.cf.mutu.dao;

import gr.aueb.cf.mutu.dto.InterestDto;

import java.util.List;

public interface IInterestDao {
    List<InterestDto> getInterestsByUserId(long id);
}
