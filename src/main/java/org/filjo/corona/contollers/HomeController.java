package org.filjo.corona.contollers;

import org.filjo.corona.models.LocationStats;
import org.filjo.corona.services.BaseService;
import org.filjo.corona.services.CoronaDeathService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class HomeController {

    final BaseService coronaService;

    public HomeController(BaseService coronaService, CoronaDeathService coronaDeathService) {
        this.coronaService = coronaService;
        this.coronaDeathService = coronaDeathService;
    }

    @GetMapping("/")
    public String greeting() {
        return "index";
    }

    @GetMapping("/cases")
    public String cases(Model model) {
        countAllStats(model, coronaService);
        return "cases";
    }

    private void countAllStats(Model model, BaseService coronaService) {
        List<LocationStats> allStats = coronaService.getAllStats();
        int totalReportedCases = allStats.stream().mapToInt(LocationStats::getLatestTotalCases).sum();
        int totalNewCases = allStats.stream().mapToInt(LocationStats::getDiffFromPrevDay).sum();
        model.addAttribute("locationStats", allStats);
        model.addAttribute("totalReportedCases", totalReportedCases);
        model.addAttribute("totalNewCases", totalNewCases);
    }

    final CoronaDeathService coronaDeathService;

    @GetMapping("/death")
    public String death(Model model) {
        countAllStats(model, coronaDeathService);
        return "death";
    }
}
