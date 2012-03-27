package com.numericalactivity.dktxtools.pool;

public interface PoolInterface {
    /**
     * Remet l'objet à son état d'origine, comme s'il venait d'être crée
     */
    public void reset();

    /**
     * Marque l'objet comme étant réutilisable et l'ajoute au pool
     */
    public void recycle();
}
