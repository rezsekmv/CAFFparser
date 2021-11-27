/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { PageUserDto } from '../models/PageUserDto';
import type { UserDto } from '../models/UserDto';
import type { CancelablePromise } from '../core/CancelablePromise';
import { request as __request } from '../core/request';

export class AdminService {

    /**
     * Felhasználó megtekintése azonosító alapján
     * @param id 
     * @returns UserDto OK
     * @throws ApiError
     */
    public static getUser(
id: number,
): CancelablePromise<UserDto> {
        return __request({
            method: 'GET',
            path: `/api/admin/user/${id}`,
        });
    }

    /**
     * Felhasználó módosítása azonosító alapján
     * @param id 
     * @param requestBody 
     * @returns UserDto OK
     * @throws ApiError
     */
    public static updateUser(
id: number,
requestBody: UserDto,
): CancelablePromise<UserDto> {
        return __request({
            method: 'PUT',
            path: `/api/admin/user/${id}`,
            body: requestBody,
            mediaType: 'application/json',
        });
    }

    /**
     * Felhasználó eltávolítása azonosító alapján
     * @param id 
     * @returns any OK
     * @throws ApiError
     */
    public static deleteUser(
id: number,
): CancelablePromise<any> {
        return __request({
            method: 'DELETE',
            path: `/api/admin/user/${id}`,
        });
    }

    /**
     * Összes felhasználó megtekintése
     * @returns PageUserDto OK
     * @throws ApiError
     */
    public static getAllUser(): CancelablePromise<PageUserDto> {
        return __request({
            method: 'GET',
            path: `/api/admin/user`,
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
            path: `/api/admin/image/${id}`,
        });
    }

}