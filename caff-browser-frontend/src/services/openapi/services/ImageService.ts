/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { ImageDto } from '../models/ImageDto';
import type { PageImageDto } from '../models/PageImageDto';
import type { CancelablePromise } from '../core/CancelablePromise';
import { request as __request } from '../core/request';

export class ImageService {

    /**
     * Összes kép megtekintése szűréssel
     * @param size 
     * @param page 
     * @param credit 
     * @param caption 
     * @returns PageImageDto OK
     * @throws ApiError
     */
    public static getAllImage(
size: number,
page: number,
credit?: string,
caption?: string,
): CancelablePromise<PageImageDto> {
        return __request({
            method: 'GET',
            path: `/api/image`,
            query: {
                'size': size,
                'page': page,
                'credit': credit,
                'caption': caption,
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