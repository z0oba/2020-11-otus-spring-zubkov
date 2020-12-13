package ru.otus.homework.service;

import org.springframework.stereotype.Service;

import java.util.List;

/**
 *  Service for limit exceeded check
 */
@Service
public class CheckErrorLimitService {

    public boolean isLimitExceeded(int errorLimit, List<Boolean> results){
        int counter = 0;
        for(Boolean result : results){
            if(!result)
                ++counter;
            if(counter > errorLimit)
                return true;
        }
        return false;
    }
}
