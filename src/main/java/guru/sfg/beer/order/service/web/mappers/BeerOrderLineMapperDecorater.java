package guru.sfg.beer.order.service.web.mappers;

import java.util.Optional;

import org.mapstruct.Qualifier;
import org.springframework.beans.factory.annotation.Autowired;

import guru.sfg.beer.order.service.domain.BeerOrderLine;
import guru.sfg.beer.order.service.services.beer.BeerService;
import guru.sfg.beer.order.service.services.beer.model.BeerDto;
import guru.sfg.beer.order.service.web.model.BeerOrderLineDto;

public abstract class BeerOrderLineMapperDecorater implements BeerOrderLineMapper {

	private BeerService beerService;
	private BeerOrderLineMapper mapper;
	
	@Autowired
	public void setBeerService(BeerService beerService) {
		this.beerService = beerService;
	}
	
	@Autowired
	//@Qualifier("delegate")
	public void setBeerOrderLineMapper(BeerOrderLineMapper beerOrderLineMapper) {
		this.mapper = beerOrderLineMapper;
	}
	
	@Override
	public BeerOrderLineDto beerOrderLineToDto(BeerOrderLine line) {
		BeerOrderLineDto beerOrderLineDto = mapper.beerOrderLineToDto(line);
		
		Optional<BeerDto> beerDtoOptional = beerService.getBeerByUpc(line.getUpc());
		
		beerDtoOptional.ifPresent(beerDto -> {
			beerOrderLineDto.setBeerName(beerDto.getBeerName());
			beerOrderLineDto.setBeerId(beerDto.getId());
		});
		
		return beerOrderLineDto;
	}

}
