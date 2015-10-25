package controllers;

import model.Result;
import model.ResultsSummary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
@EnableAutoConfiguration
@RequestMapping("/results-summary")
public class ResultsSummaryController {
    private FileWriterService fileWriterService;

    @Autowired
    public ResultsSummaryController(FileWriterService fileWriterService) {
        this.fileWriterService = fileWriterService;
    }

    @RequestMapping(method= RequestMethod.GET)
    public List<ResultsSummary> getTime() {
        return calculateResultSummary();
    }

    public List<ResultsSummary> calculateResultSummary() {
        List<Result> modifiableResults = fileWriterService.readFromFile();

        Collections.sort(modifiableResults, new Comparator<Result>() {

            public int compare(Result r1, Result r2) {
                if(r1.getLayout() > r2.getLayout()) {
                    return 1;
                } else if(r1.getLayout() == r2.getLayout()){
                    return (r1.getStartTime() > r2.getStartTime())? 1 : -1;
                }else {
                    return -1;
                }

            }
        });

        List<Result> modifiableResultSummary = new ArrayList<>(modifiableResults);
        Collections.sort(modifiableResultSummary, new Comparator<Result>() {

            public int compare(Result r1, Result r2) {
                return (r1.getEndTime() - r1.getStartTime() < r2.getEndTime() - r2.getStartTime())? -1 : 1;
            }
        });

        List<ResultsSummary> resultsSummary = new ArrayList<>();

        for(String carNumber : getAListOfUniqueCarNumbers(modifiableResults)){
            resultsSummary.add(new ResultsSummary(carNumber, getAListOfTimeTaken(modifiableResults, carNumber), calculateTotal(modifiableResults, carNumber)));
        }

        Collections.sort(resultsSummary, new Comparator<ResultsSummary>() {
            public int compare(ResultsSummary r1, ResultsSummary r2) {
                return (r1.getTotal() < r2.getTotal())? -1 : 1;
            }
        });

        return resultsSummary;
    }


    private long calculateTotal(List<Result> result, String carNumber) {
        long total = 0;
        for(int i =0; i < result.size(); i++) {
            if(result.get(i).getCarNumber().equals(carNumber)) {
                int penalty = result.get(i).getPenalty() * 5;
                total+= (result.get(i).getEndTime() - result.get(i).getStartTime() + penalty);
            }
        }
        return total;
    }

    private List<Long> getAListOfTimeTaken(List<Result> result, String carNumber) {
        List<Long> timeTaken = new ArrayList<>();
        for(int i =0; i < result.size(); i++) {
            if(result.get(i).getCarNumber().equals(carNumber)) {
                int penalty = result.get(i).getPenalty() * 5;
                timeTaken.add((result.get(i).getEndTime() - result.get(i).getStartTime()) + penalty);

            }
        }
        return timeTaken;
    }

    private Set<String> getAListOfUniqueCarNumbers(List<Result> result) {
        Set<String> carNumbers = new HashSet<>();
        for(Result res : result){
            carNumbers.add(res.getCarNumber());
        }
        return  carNumbers;
    }
}
