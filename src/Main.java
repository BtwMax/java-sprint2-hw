import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        MonthlyReport[] monthlyReport = new MonthlyReport[3];
        for (int i = 0; i < monthlyReport.length; i++) {
            monthlyReport[i] = new MonthlyReport();
        }
        YearlyReport yearlyReport = new YearlyReport();

        printMenu();

        while(true) {
            int userInput = scanner.nextInt();
            if (userInput == 1) {
                for (int i = 0; i < monthlyReport.length; i++) {
                    monthlyReport[i].reader("resources/m.20210" + (i + 1) + ".csv");
                }
                System.out.println("Месячные отчёты считаны" + '\n');
            } else if (userInput == 2) {
                yearlyReport.reader("resources/y.2021.csv");
                System.out.println("Годовой отчет считан" + '\n');
            } else if (userInput == 3) {
                for (int i = 0; i < monthlyReport.length; i++) {
                    if (yearlyReport.yearlyData.isEmpty() && monthlyReport[i].monthlyData.isEmpty()) {
                        System.out.println("Невозможно провести сверку, отчёты не считаны");
                    } else if (yearlyReport.sumAllMonthProfit(i) != monthlyReport[i].sumAllProfit()) {
                        System.out.println("Доходы не совпадают в" + (i + 1) + " месяце");
                    } else if (yearlyReport.sumAllMonthExpenses(i) != monthlyReport[i].sumAllExpenses()) {
                        System.out.println("Расходы не совпадают в" + (i + 1) + " месяце");
                    }
                }
                System.out.println("Сверка завершена, ошибок не обнаружено" + '\n');
            } else if (userInput == 4) {
                for (int i = 0; i < monthlyReport.length; i++) {
                    if (monthlyReport[i].monthlyData.isEmpty()) {
                        System.out.println("Отчеты по месяцам не считаны, нажмите 1 чтобы считать отчёты" + '\n');
                    } else {
                            System.out.println("Информация за месяц " + (i + 1));
                            monthlyReport[i].printMonthlyReport();
                    }
                }
            } else if (userInput == 5) {
                if (yearlyReport.yearlyData.isEmpty()) {
                    System.out.println("Годовой отчёт не считан, нажмите 2 чтобы считать отчёт" + '\n');
                } else
                    yearlyReport.printYearlyReport();
            } else if (userInput == 0) {
                break;
            } else
                System.out.println("Данная команда не поддерживается" + '\n');
            printMenu();
        }
        System.out.println("Программа завершена");
    }

    private static void printMenu() {
        System.out.println("1. Считать все месячные отчёты");
        System.out.println("2. Считать годовой отчёт");
        System.out.println("3. Сверить отчёты");
        System.out.println("4. Вывести информацию о всех месячных отчётах");
        System.out.println("5. Вывести информацию о годовом отчёте");
        System.out.println("0. Выйти из программы");
    }
}

