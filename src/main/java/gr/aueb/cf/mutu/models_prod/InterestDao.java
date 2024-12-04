package gr.aueb.cf.mutu.models_prod;

import gr.aueb.cf.mutu.dao.IInterestDao;
import gr.aueb.cf.mutu.dto.InterestDto;
import gr.aueb.cf.mutu.models_dev.Interest;

import java.util.List;
import java.util.stream.Collectors;

public class InterestDao implements IInterestDao {
    @Override
    public List<InterestDto> getInterestsByUserId(long id) {
        return null;
    }
}
