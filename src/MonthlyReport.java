import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;

public class MonthlyReport {

    HashMap<String, MonthlyDataInfo> monthlyData;

    public MonthlyReport() {
        monthlyData = new HashMap<>();
    }

    /*Метод для считывания данных из файлов и их распределения по прибыли и расходам*/
    void reader (String path) {
        String content = readFileContents(path);
        if (content == null) { /*Как я понял так нужно сделать?*/
            return;
        }
        String[] lines = content.split("\r?\n");
        for (int i = 1; i < lines.length; i++) {
            String line = lines[i];
            String[] dataLine = line.split(",");
            String itemName = dataLine[0];
            boolean isExpense = Boolean.parseBoolean(dataLine[1]);
            int quantity  = Integer.parseInt(dataLine[2]);
            int sum_of_one = Integer.parseInt(dataLine[3]);
            if (!monthlyData.containsKey(itemName)) {
                monthlyData.put(itemName, new MonthlyDataInfo());
            }
            MonthlyDataInfo monthlyDataInfo = monthlyData.get(itemName);
            if (isExpense) {
                monthlyDataInfo.expense += quantity * sum_of_one;
            } else {
                monthlyDataInfo.profit += quantity * sum_of_one;
            }
        }
    }

    /*Метод чтения файла по строкам из переданной директории*/
    String readFileContents(String path) {
        try {
            return Files.readString(Path.of(path));
        } catch (IOException e) {
            System.out.println("Невозможно прочитать файл с месячным отчётом. Возможно, файл не находится в нужной директории.");
            return null;
        }
    }

    /*Метод подсчета суммы доходов*/
    int sumAllProfit() {
        int sumProfit = 0;
        for (String name : monthlyData.keySet()) {
            MonthlyDataInfo monthlyDataInfo = monthlyData.get(name);
            sumProfit += monthlyDataInfo.profit;
        }
        return sumProfit;
    }

    /*Метод подсчета суммы расходов*/
    int sumAllExpenses() {
        int sumExpenses = 0;
        for (String name : monthlyData.keySet()) {
            MonthlyDataInfo monthlyDataInfo = monthlyData.get(name);
            sumExpenses += monthlyDataInfo.expense;
        }
        return sumExpenses;
    }

    /*Метод, который определяет максимальные траты и прибыль за месяц и печатает эту информацию*/
    void printMonthlyReport() {
        String productProfitName = "";
        String productExpenseName = "";
        int maxProfit = 0;
        int maxExpense = 0;
        for (String name : monthlyData.keySet()) {
            MonthlyDataInfo monthlyDataInfo = monthlyData.get(name);
            if (monthlyDataInfo.profit > maxProfit) {
                maxProfit = monthlyDataInfo.profit;
                productProfitName = name;
            }
            if (monthlyDataInfo.expense > maxExpense) {
                maxExpense = monthlyDataInfo.expense;
                productExpenseName = name;
            }
        }
        System.out.println("Самая высокая прибыль: " + productProfitName + " - " + maxProfit);
        System.out.println("Самая большая трата: " + productExpenseName + " - " + maxExpense);
    }
}
