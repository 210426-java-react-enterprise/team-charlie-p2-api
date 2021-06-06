package com.revature.pantry.services;

import com.revature.pantry.models.MealPlan;
import com.revature.pantry.models.MealTime;
import org.springframework.stereotype.Service;

import java.util.function.BiPredicate;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class MealService {

//    private MecipeRepository mealRepository;
//
//    @Autowired
//    public RecipeService(MealRepository mealRepository){this.mealRepository = mealRepository;}
    
    /**
     * This method is responsible for validate the meal time data inputs against the app constraints
     * @param mealTime - meal time data to be audit
     * @return TRUE if data passed all the constraints / FALSE if not passed the constrains
     */
    
    public boolean isMealTimeValid(MealTime mealTime){
    
        //Evaluates if the string passed is null or empty
        Predicate<String> isNullOrEmpty = str -> (str == null || str.trim().isEmpty());
    
        //Evaluates if the string passed is inside of the length range
        BiPredicate<String, Integer> isLengthValid = (str, lenght) -> str.length() <= lenght;
    
        //Evaluates if the string passed satisfied the pattern passed
        BiPredicate<String, String> isPatternSatisfied = (str, pattern) -> {
            Pattern patternToCompile = Pattern.compile(pattern);
            Matcher patternValidation = patternToCompile.matcher(str);
            return patternValidation.matches();
        };
        
        //Evaluate if the string is equal to the accepted values
        Predicate<String> isValidTime = str -> {
            switch(str.toLowerCase()){
                case "breakfast":
                case "dinner":
                case "lunch":
                case "snack":
                    return true;
                default:
                    return false;
            }
        };
        
        if(mealTime == null) return false;
        //mealTime > Contains alphabetic characters and we accept the strings breakfast, lunch, dinner and snack as value
        if(isNullOrEmpty.test(mealTime.getMealTime()) || !isPatternSatisfied.test(mealTime.getMealTime(),"^[A-Za-z]+$") || !isValidTime.test(mealTime.getMealTime()))return false;
    
        //Return this if passed all the constraints
        return true;
    }
    
    /**
     * This method is responsible for validate the meal plan data inputs against the app constraints
     * @param mealPlan - meal plan data to be audit
     * @return TRUE if data passed all the constraints / FALSE if not passed the constrains
     */
    
    public boolean isMealPlanValid(MealPlan mealPlan){
        //Evaluates if the string passed is null or empty
        Predicate<String> isNullOrEmpty = str -> (str == null || str.trim().isEmpty());
        
        //Evaluates if the string passed satisfied the pattern passed
        BiPredicate<String, String> isPatternSatisfied = (str, pattern) -> {
            Pattern patternToCompile = Pattern.compile(pattern);
            Matcher patternValidation = patternToCompile.matcher(str);
            return patternValidation.matches();
        };
        
    
        //date > Not null or Not empty & satisfied the date format mm/dd/yyyy
        if(isNullOrEmpty.test(String.valueOf(mealPlan.getDate())) || !isPatternSatisfied.test(String.valueOf(mealPlan.getDate()), "^(0[1-9]|1[012])[- /.](0[1-9]|[12][0-9]|3[01])[- /.](19|20)\\d\\d$")) return false;
        
        //user > Not null (I'm assuming that the user id exist in the DB)
        if(isNullOrEmpty.test(String.valueOf(mealPlan.getUser().getId()))) return false;
        
        //Return this if passed all the constraints
        return true;
    }
    
}
