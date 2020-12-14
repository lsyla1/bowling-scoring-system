package com.jobsity.bowling.service;

/**
 * System interface
 */
public interface SystemService {

    /**
     * Process a new game
     * @param filePath File path of a given game
     * @return the dashboard results
     * @throws Exception
     */
    String processGame(String filePath) throws Exception;
}
