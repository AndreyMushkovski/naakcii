package naakcii.by.api.country;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

public interface CountryRepository extends CrudRepository<Country, Long> {
	
	Optional<Country> findByAlphaCode2(String alphaCode2);
}