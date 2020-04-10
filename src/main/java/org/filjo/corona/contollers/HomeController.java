package org.filjo.corona.contollers;

import org.filjo.corona.models.LocationStats;
import org.filjo.corona.services.CoronaDeathService;
import org.filjo.corona.services.CoronaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class HomeController {

    @Autowired
    CoronaService coronaService;

    @GetMapping("/")
    public String greeting() {
        return "index";
    }

    @GetMapping("/cases")
    public String cases(Model model) {
        List<LocationStats> allStats = coronaService.getAllStats();
        int totalReportedCases = allStats.stream().mapToInt(stat -> stat.getLatestTotalCases()).sum();
        int totalNewCases = allStats.stream().mapToInt(stat -> stat.getDiffFromPrevDay()).sum();
        model.addAttribute("locationStats", allStats);
        model.addAttribute("totalReportedCases", totalReportedCases);
        model.addAttribute("totalNewCases", totalNewCases);
        return "cases";
    }

    @Autowired
    CoronaDeathService coronaDeathService;

    @GetMapping("/death")
    public String death(Model model) {
        List<LocationStats> allStats = coronaDeathService.getAllStats();
        int totalReportedCases = allStats.stream().mapToInt(stat -> stat.getLatestTotalCases()).sum();
        int totalNewCases = allStats.stream().mapToInt(stat -> stat.getDiffFromPrevDay()).sum();
        model.addAttribute("locationStats", allStats);
        model.addAttribute("totalReportedCases", totalReportedCases);
        model.addAttribute("totalNewCases", totalNewCases);
        return "death";
    }
}
