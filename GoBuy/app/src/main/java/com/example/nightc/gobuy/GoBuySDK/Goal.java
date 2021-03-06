package com.example.nightc.gobuy.GoBuySDK;

import org.joda.time.Days;
import org.joda.time.LocalDate;

/**
 * Created by nightc on 6/25/17.
 */

/* the calculations will dynamically change when the product price needs less than a day to buy */
public class Goal {

    private Item GoalItem;
    private Income incomes;
    private Bill Expenses;
    private GoalDates Dates; //API for all the Goal dates that we have to use
    private GoalMoneySaved MoneySaved; //API for the money that have been saved for the goal
    private GoalMoneyToSavePerDay MoneyToSavePerDay; //a class that calculates the money to be saved each day
    private double MoneySavedPerMonth; //WARNING!!! TO BE CONVERTED TO DAILY,Desription: Money you save each day and have available to spend
    private Day day;

    public Goal(Item goalItem,Income incomes, Bill Expenses, LocalDate dateWanted, double moneySaved) {
        GoalItem = goalItem;
        this.incomes = incomes; //user will add his incomes on the Oncreate,and then they will be added here
        this.Expenses = Expenses;
        Dates = new GoalDates(dateWanted);
        MoneySaved = new GoalMoneySaved(moneySaved);
        MoneyToSavePerDay = new GoalMoneyToSavePerDay(Dates,GoalItem); //May change it to Dates.get and Items.get
        CalMoneySavedPerMonth();
        startNewDay();

    }



    public boolean Achievable(){
        if (Days.daysBetween(Dates.getExpectedDate(),Dates.getDateWanted()).getDays() >= 0)
            return true;
        return false;
    }



    //WARNING!!! THIS SECTION MAY BE BETTER IF IT WAS ADDED INTO THE USER DATA(maybe not)


    //Calculates the money the user Saves each month
    //WARNING!!! this has to be converted to daily
    //maybe we should prompt the user to insert his Annual Income and then we divide it
    //by the Working Days of each year to calculate the daily income
    //ELSE we ask for the monthly Income and divide it by the working days(or all the days of the month since the next method calculates even the non working days)
    //to calculate the daily income
    public void CalMoneySavedPerMonth(){
        MoneySavedPerMonth = (incomes.getTotalIncome() - Expenses.getTotalAmount());
    }



    //Starts a new day each time it changes,to reset the daily tracking status
    public void startNewDay(){
        day = new Day(MoneySavedPerMonth - MoneyToSavePerDay.getMoneyToSavePerDay(),MoneyToSavePerDay.getMoneyToSavePerDay());
    }













    //Getters
    public Item getGoalItem() {
        return GoalItem;
    }

    public Income getIncomes() {
        return incomes;
    }

    public Bill getExpenses() {
        return Expenses;
    }
}
