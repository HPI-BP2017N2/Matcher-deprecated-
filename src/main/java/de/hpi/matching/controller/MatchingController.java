package de.hpi.matching.controller;

import de.hpi.matching.dto.MatchingResponse;
import de.hpi.matching.dto.RestMatchingConfig;
import de.hpi.matching.service.MatchingService;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class MatchingController {
    @Getter(AccessLevel.PRIVATE) @Setter(AccessLevel.PRIVATE) private MatchingService service;

    @Autowired
    public MatchingController(MatchingService service){
        setService(service);
    }

    @RequestMapping(value = "/match", method = RequestMethod.POST, produces = "application/json")
    public MatchingResponse startMatching(@RequestBody RestMatchingConfig restMatchingConfig){
        return service.match(restMatchingConfig.getShopId(), restMatchingConfig.getOfferTitle(), restMatchingConfig.getEan(), restMatchingConfig.getHan(), restMatchingConfig.getSku(), restMatchingConfig.getUrl(), restMatchingConfig.getPrice(), restMatchingConfig.getCategoryString());
    }
}
