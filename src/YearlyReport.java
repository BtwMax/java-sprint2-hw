import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;

public class YearlyReport {

    HashMap<Integer, YearlyDataInfo> yearlyData;
    int monthPeriod;
    public YearlyReport() {
        yearlyData = new HashMap<>();
        monthPeriod = 3;

    }

    /*Метод для считывания данных из файлов и их распределения по прибыли и расходам*/
    void reader (String path) {
        String content = readFileContents(path);
        String[] lines = content.split("\r?\n");
        for (int i = 1; i < lines.length; i++) {
            String line = lines[i];
            String[] dataLine = line.split(",");
            int month = Integer.parseInt(dataLine[0]);
            int amount = Integer.parseInt(dataLine[1]);
            boolean isExpense = Boolean.parseBoolean(dataLine[2]);
            if (!yearlyData.containsKey(month)) {
                yearlyData.put(month, new YearlyDataInfo());
            }
            YearlyDataInfo yearlyDataInfo = yearlyData.get(month);
            if (isExpense) {
                yearlyDataInfo.expense += amount;
            } else {
                yearlyDataInfo.profit += amount;
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

    /*Сумма всех доходов за определенный месяц*/
    int sumAllMonthProfit (int month) {
        YearlyDataInfo yearlyDataInfo = yearlyData.get(month + 1);
        return yearlyDataInfo.profit;
    }

    /*Сумма всех расходов за определенный месяц*/
    int sumAllMonthExpenses (int month) {
        YearlyDataInfo yearlyDataInfo = yearlyData.get(month + 1);
        return yearlyDataInfo.expense;
    }

    /*Метод, который определяет максимальные траты и прибыль за год и печатает эту информацию*/
    public void printYearlyReport() {
        System.out.println("2021 год:");
        for (Integer month : yearlyData.keySet()) {
            YearlyDataInfo yearlyDataInfo = yearlyData.get(month);
            System.out.println("За " + month + " месяц " + "прибыль составила: " + (yearlyDataInfo.profit - yearlyDataInfo.expense));
        }

        int sumAllExpenses = 0;
        for (Integer month : yearlyData.keySet()) {
            YearlyDataInfo yearlyDataInfo = yearlyData.get(month);
            sumAllExpenses += yearlyDataInfo.expense;
        }
        System.out.println("Средний расход выбранные месяцы составил: " + sumAllExpenses/monthPeriod);

        int amountOfProfit = 0;
        for (Integer month : yearlyData.keySet()) {
            YearlyDataInfo yearlyDataInfo = yearlyData.get(month);
            amountOfProfit += yearlyDataInfo.profit;
        }
        System.out.println("Средний доход за выбранные месяцы составил: " + amountOfProfit/monthPeriod);

    }

}
