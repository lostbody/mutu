package gr.aueb.cf.mutu.models_dev;

import gr.aueb.cf.mutu.dao.IInterestDao;
import gr.aueb.cf.mutu.dto.InterestDto;

import java.util.List;
import java.util.stream.Collectors;

public class InterestDao implements IInterestDao {
    @Override
    public List<InterestDto> getInterestsByUserId(long id) {
        return Interest.getInterestsByUserId(id).stream()
                .map(Interest::toDto)
                .collect(Collectors.toList());
    }
}
