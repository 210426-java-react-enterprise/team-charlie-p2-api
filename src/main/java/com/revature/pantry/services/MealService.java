package com.revature.pantry.services;

import com.revature.pantry.exceptions.MealDataIsInvalid;
import com.revature.pantry.exceptions.RecipeDataIsInvalid;
import com.revature.pantry.exceptions.UserDataIsInvalid;
import com.revature.pantry.models.MealPlan;
import com.revature.pantry.models.MealTime;
import org.springframework.stereotype.Service;

import java.util.Locale;
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
     * This method is responsible for validate the meal plan data inputs against the app constraints
     * @param mealPlan - meal plan data to be audit
     * @return TRUE if data passed all the constraints / FALSE if not passed the constrains
     */
    
    public boolean isMealPlanValid(MealPlan mealPlan) throws MealDataIsInvalid {
        
        
        //mealPlan cannot be null
        if(mealPlan == null){throw new MealDataIsInvalid("Please provide a not null object");}
    
        try{
            //DATE
            //Not null or Empty
            isNullOrEmpty("Date", String.valueOf(mealPlan.getDate()));
            //Must contains numeric characters
            isPatternSatisfied("Date",
                               "^(0[1-9]|1[012])[- /.](0[1-9]|[12][0-9]|3[01])[- /.](19|20)\\d\\d$",
                               String.valueOf(mealPlan.getDate()),
                               "Not valid date format");
        
            //USER
            // Not Null or empty
            isNullOrEmpty("User", String.valueOf(mealPlan.getUser().getId()));

        
            
        
        }catch(MealDataIsInvalid e){
            throw e;
        }
    
        //Return this if passed all the constraints
        return true;
        
    }
    
    
    /**
     * This method is responsible for validate the meal time data inputs against the app constraints
     * @param mealTime - meal time data to be audit
     * @return TRUE if data passed all the constraints / FALSE if not passed the constrains
     */
    
    public boolean isMealTimeValid(MealTime mealTime) throws MealDataIsInvalid{
        
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
        
        //mealTime Object cannot be null
        if(mealTime == null){throw new MealDataIsInvalid("Please provide a not null object");}
    
        try{
            //MEAL TIME
            //Not null or Empty
            isNullOrEmpty("Meal Time", String.valueOf(mealTime.getMealTime()));
            //Contains alphabetic characters
            isPatternSatisfied("Meal Time",
                               "^[A-Za-z]+$",
                               mealTime.getMealTime(),
                               "Must contain alphabetic characters");
            
        }catch(MealDataIsInvalid e){
            throw e;
        }
    
    
        if(!isValidTime.test(mealTime.getMealTime().toLowerCase(Locale.ROOT))){
            throw new MealDataIsInvalid();
        }
        
        //Return this if passed all the constraints
        return true;
        
    }
    
    /**
     * This method is part of isMealPlanValid and isMealTimeValid
     *
     * @param field - that we want to evaluate
     * @param strToEval - the string value from the field to be evaluated
     * @throws UserDataIsInvalid - Exception returned if the strToEval doesn't pass the evaluation
     */
    private void isNullOrEmpty(String field,String strToEval) throws MealDataIsInvalid {
        
        //Evaluates if the string passed is null or empty
        Predicate<String> isNullOrEmptyPredicate = str -> (str == null || str.trim().isEmpty());
        
        if(isNullOrEmptyPredicate.test(strToEval)) { throw new MealDataIsInvalid(field+ ": empty or null");}
    }
    
    /**
     * This method is part of isMealPlanValid and isMealTimeValid
     *
     * @param field - that we want to evaluate
     * @param limit - to be evaluated with length of string
     * @param strToEval - the string value from the field to be evaluated
     * @throws UserDataIsInvalid - Exception returned if the strToEval doesn't pass the evaluation
     */
    private void isLengthValid(String field, int limit, String strToEval) throws MealDataIsInvalid{
        
        //Evaluates if the string passed is inside of the length range
        BiPredicate<String, Integer> eval = (str, length) -> str.length() > length;
        
        if(!eval.test(strToEval,20)){throw new MealDataIsInvalid(field+ ": has more than "+limit+" characters");}
        
    }
    
    /**
     * This method is part of isMealPlanValid and isMealTimeValid
     *
     * @param field - that we want to evaluate
     * @param inputPattern - used to run the evaluation
     * @param strToEval - the string value from the field to be evaluated
     * @param exceptionMessage - Message in case of exception
     * @throws UserDataIsInvalid - Exception returned if the strToEval doesn't pass the evaluation
     */
    private void isPatternSatisfied(String field, String inputPattern, String strToEval, String exceptionMessage) throws MealDataIsInvalid{
        
        //Evaluates if the string passed satisfied the pattern passed
        BiPredicate<String, String> eval = ((str, pattern) -> {
            Pattern patternToCompile = Pattern.compile(pattern);
            Matcher patternValidation = patternToCompile.matcher(str);
            return patternValidation.matches();});
        
        if(!eval.test(strToEval, inputPattern)){ throw new MealDataIsInvalid(field+": "+exceptionMessage);};
    }
    
}
