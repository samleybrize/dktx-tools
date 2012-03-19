package com.numericalactivity.dktxtools.dds;

final class DDSUtil {

	private DDSUtil() {		
	}

	/**
	 * Retourne la taille d'un block (en byte) pour le format de compression spécifié
	 * @param fourCC attribut "fourCC" des entêtes spécifiant le format de compression
	 */
	public static int getCompressedBlockSize(int fourCC) {
		switch (fourCC) {
    		case DDSFourCC.FOURCC_DXT1:
    		case DDSFourCC.FOURCC_ATC:
    		case DDSFourCC.FOURCC_ETC1:
    			return 8;

			default:
			    return 16;
		}
	}
	
}
