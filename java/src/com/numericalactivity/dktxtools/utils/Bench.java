package com.numericalactivity.dktxtools.utils;

/**
 * Utilitaire de benchmark
 */
abstract public class Bench {

    protected static final byte SIZE            = 10;

    protected static long[] _counters; // compteurs
    protected static long[] _numberOfSequences; // nombre de fois que chaque compteur a été démarré et stoppé
    protected static long[] _tempMillis; // timestamps temporaires

    protected Bench() {}

    /**
     * Initialise la classe
     */
    protected static void initialize() {
        if (null == _counters) {
            _counters           = new long[SIZE];
            _numberOfSequences  = new long[SIZE];
            _tempMillis         = new long[SIZE];
        }
    }

    /**
     * Log un message
     * @param string
     */
    protected static void log(String string) {
        
    }

    /**
     * Démarre un chrono
     * @param tag clé à associer au chrono
     */
    public static void start(int index) {
        if (null == _counters) {
            initialize();
        }

        _tempMillis[index] = System.nanoTime();
    }

    /**
     * Stoppe un chrono et ajoute sa valeur au compteur associé
     * @param tag clé associée au chrono
     */
    public static void stop(int index) {
        _counters[index] = (System.nanoTime() - _tempMillis[index]) + _counters[index];
        _numberOfSequences[index]++;
    }

    /**
     * Remet un compteur à zéro
     * @param tag clé associée au compteur
     */
    public static void reset(int index) {
        _counters[index]            = 0;
        _numberOfSequences[index]   = 0;
    }

    /**
     * Log la valeur de tous les compteurs
     */
    public static void log() {
        for (byte i = 0; i < SIZE; i++) {
            log(i);
        }
    }

    /**
     * Log la valeur d'un compteur
     * @param tag clé associée au compteur
     */
    public static void log(int index) {
        log(index + " : " + String.valueOf(_counters[index] + "µs (" + _numberOfSequences[index] + " sequences)"));
    }

    /**
     * Retourne la valeur de tous les compteurs
     * @return valeurs des compteurs
     */
    public static long[] get() {
        return _counters;
    }

    /**
     * Retourne la valeur d'un compteur
     * @param tag clé associée au compteur
     * @return valeur du compteur
     */
    public static long get(int index) {
        return _counters[index];
    }

    /**
     * Retourne le nombre de fois qu'un compteur associé à un tag a été démarré et stoppé
     * @param tag clé associée au compteur
     * @return
     */
    public static long getNumberOfSequences(int index) {
        return _numberOfSequences[index];
    }
}
