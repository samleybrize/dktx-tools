package com.numericalactivity.dktxtools.utils;

/**
 * Utilitaire de benchmark
 */
public class Bench {

    protected static final byte SIZE = 10;

    protected long[] _counters; // compteurs
    protected long[] _numberOfSequences; // nombre de fois que chaque compteur a été démarré et stoppé
    protected long[] _tempNano; // timestamps temporaires

    /**
     * Initialise la classe
     */
    protected void initialize() {
        if (null == _counters) {
            _counters           = new long[SIZE];
            _numberOfSequences  = new long[SIZE];
            _tempNano           = new long[SIZE];
        }
    }

    /**
     * Log un message
     * @param string
     */
    protected void log(String string) {
        
    }

    /**
     * Démarre un chrono
     * @param tag clé à associer au chrono
     */
    public void start(int index) {
        if (null == _counters) {
            initialize();
        }

        _tempNano[index] = System.nanoTime();
    }

    /**
     * Stoppe un chrono et ajoute sa valeur au compteur associé
     * @param tag clé associée au chrono
     */
    public void stop(int index) {
        _counters[index] = (System.nanoTime() - _tempNano[index]) + _counters[index];
        _numberOfSequences[index]++;
    }

    /**
     * Remet un compteur à zéro
     * @param tag clé associée au compteur
     */
    public void reset(int index) {
        _counters[index]            = 0;
        _numberOfSequences[index]   = 0;
    }

    /**
     * Log la valeur de tous les compteurs
     */
    public void log() {
        for (byte i = 0; i < SIZE; i++) {
            log(i);
        }
    }

    /**
     * Log la valeur d'un compteur
     * @param tag clé associée au compteur
     */
    public void log(int index) {
        log(index + " : " + String.valueOf(_counters[index] / 1000000) + "µs (" + String.valueOf(_numberOfSequences[index]) + " sequences)");
    }

    /**
     * Retourne la valeur de tous les compteurs
     * @return valeurs des compteurs
     */
    public long[] get() {
        return _counters;
    }

    /**
     * Retourne la valeur d'un compteur
     * @param tag clé associée au compteur
     * @return valeur du compteur
     */
    public long get(int index) {
        return _counters[index];
    }

    /**
     * Retourne le nombre de fois qu'un compteur associé à un tag a été démarré et stoppé
     * @param tag clé associée au compteur
     * @return
     */
    public long getNumberOfSequences(int index) {
        return _numberOfSequences[index];
    }
}
