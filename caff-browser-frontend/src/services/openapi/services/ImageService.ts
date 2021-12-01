/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { ImageDto } from '../models/ImageDto';
import type { Pageable } from '../models/Pageable';
import type { PageImageDto } from '../models/PageImageDto';
import type { CancelablePromise } from '../core/CancelablePromise';
import { request as __request } from '../core/request';

export class ImageService {

    /**
     * Összes kép megtekintése
     * @param pageable 
     * @returns PageImageDto OK
     * @throws ApiError
     */
    public static getAllImage(
pageable: Pageable,
): CancelablePromise<PageImageDto> {
        return __request({
            method: 'GET',
            path: `/api/image`,
            query: {
                'pageable': pageable,
            },
        });
    }

    /**
     * Új kép létrehozása
     * @param formData 
     * @returns ImageDto OK
     * @throws ApiError
     */
    public static createImage(
formData?: {
image?: Blob;
},
): CancelablePromise<ImageDto> {
        return __request({
            method: 'POST',
            path: `/api/image`,
            formData: formData,
            mediaType: 'multipart/form-data',
        });
    }

    /**
     * Kép és annak részleteinek megtekintése azonosító alapján
     * @param id 
     * @returns ImageDto OK
     * @throws ApiError
     */
    public static getImage(
id: number,
): CancelablePromise<ImageDto> {
        return __request({
            method: 'GET',
            path: `/api/image/${id}`,
        });
    }

    /**
     * Kép eltávolítása
     * @param id 
     * @returns any OK
     * @throws ApiError
     */
    public static deleteImage(
id: number,
): CancelablePromise<any> {
        return __request({
            method: 'DELETE',
            path: `/api/image/${id}`,
        });
    }

}