package com.revature.pantry.services;

import com.revature.pantry.exceptions.MealDataIsInvalidException;
import com.revature.pantry.exceptions.UserDataIsInvalidException;
//import com.revature.pantry.models.MealPlan;
import com.revature.pantry.models.MealTime;
import com.revature.pantry.models.User;
//import com.revature.pantry.repos.MealPlanRepository;
import com.revature.pantry.repos.MealTimeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Locale;
import java.util.function.BiPredicate;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Deprecated
@Service
public class MealService {
    
    private MealTimeRepository mealTimeRepository;

    @Autowired
    public MealService(MealTimeRepository mealTimeRepository){
        this.mealTimeRepository=mealTimeRepository;
    }
    

    public MealTime saveMealTime(MealTime mealTime){
        isMealTimeValid(mealTime);
        return mealTimeRepository.save(mealTime);
        
    }
    

    //V3
    public List<MealTime> findMealTimesByDateAndUser(LocalDate date, User inputUser){
        return inputUser.getMealTimesList();
        
    }
    
    
    /**
     * This method is responsible for validate the meal time data inputs against the app constraints
     * @param mealTime - meal time data to be audit
     * @return TRUE if data passed all the constraints / FALSE if not passed the constrains
     */
    
    public boolean isMealTimeValid(MealTime mealTime) throws MealDataIsInvalidException {
        
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
        if(mealTime == null){throw new MealDataIsInvalidException("Please provide a not null object");}
    
        try{
            
            //Moved from MealPlan
            //DATE
            //Not null or Empty
            isNullOrEmpty("Date", String.valueOf(mealTime.getDate()));
            //Must contains numeric characters
            isPatternSatisfied("Date",
                               "^\\d{4}\\-(0[1-9]|1[012])\\-(0[1-9]|[12][0-9]|3[01])$",
                               String.valueOf(mealTime.getDate()),
                               "Not valid date format");
            //MEAL TIME
            //Not null or Empty
            isNullOrEmpty("Meal Time", String.valueOf(mealTime.getMealTime()));
            //Contains alphabetic characters
            isPatternSatisfied("Meal Time",
                               "^[A-Za-z]+$",
                               mealTime.getMealTime(),
                               "Must contain alphabetic characters");
            
        }catch(MealDataIsInvalidException e){
            throw e;
        }
    
    
        if(!isValidTime.test(mealTime.getMealTime().toLowerCase(Locale.ROOT))){
            throw new MealDataIsInvalidException();
        }
        
        //Return this if passed all the constraints
        return true;
        
    }
    
    /**
     * This method is part of isMealPlanValid and isMealTimeValid
     *
     * @param field - that we want to evaluate
     * @param strToEval - the string value from the field to be evaluated
     * @throws UserDataIsInvalidException - Exception returned if the strToEval doesn't pass the evaluation
     */
    private void isNullOrEmpty(String field,String strToEval) throws MealDataIsInvalidException {
        
        //Evaluates if the string passed is null or empty
        Predicate<String> isNullOrEmptyPredicate = str -> (str == null || str.trim().isEmpty());
        
        if(isNullOrEmptyPredicate.test(strToEval)) { throw new MealDataIsInvalidException(field+ ": empty or null");}
    }
    
    /**
     * This method is part of isMealPlanValid and isMealTimeValid
     *
     * @param field - that we want to evaluate
     * @param limit - to be evaluated with length of string
     * @param strToEval - the string value from the field to be evaluated
     * @throws UserDataIsInvalidException - Exception returned if the strToEval doesn't pass the evaluation
     */
    private void isLengthValid(String field, int limit, String strToEval) throws MealDataIsInvalidException {
        
        //Evaluates if the string passed is inside of the length range
        BiPredicate<String, Integer> eval = (str, length) -> str.length() > length;
        
        if(!eval.test(strToEval,20)){throw new MealDataIsInvalidException(field+ ": has more than "+limit+" characters");}
        
    }
    
    /**
     * This method is part of isMealPlanValid and isMealTimeValid
     *
     * @param field - that we want to evaluate
     * @param inputPattern - used to run the evaluation
     * @param strToEval - the string value from the field to be evaluated
     * @param exceptionMessage - Message in case of exception
     * @throws UserDataIsInvalidException - Exception returned if the strToEval doesn't pass the evaluation
     */
    private void isPatternSatisfied(String field, String inputPattern, String strToEval, String exceptionMessage) throws MealDataIsInvalidException {
        
        //Evaluates if the string passed satisfied the pattern passed
        BiPredicate<String, String> eval = ((str, pattern) -> {
            Pattern patternToCompile = Pattern.compile(pattern);
            Matcher patternValidation = patternToCompile.matcher(str);
            return patternValidation.matches();});
        
        if(!eval.test(strToEval, inputPattern)){ throw new MealDataIsInvalidException(field+": "+exceptionMessage);};
    }
    
}
