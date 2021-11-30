/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { CancelablePromise } from '../core/CancelablePromise';
import { request as __request } from '../core/request';

export class HelloService {

    /**
     * Tesztvégpont publikus végpontok ellenőrzésére
     * @returns string OK
     * @throws ApiError
     */
    public static helloPublic(): CancelablePromise<string> {
        return __request({
            method: 'GET',
            path: `/api/public/hello`,
        });
    }

    /**
     * Tesztvégpont moderátori jog ellenőrzésére
     * @returns string OK
     * @throws ApiError
     */
    public static helloMod(): CancelablePromise<string> {
        return __request({
            method: 'GET',
            path: `/api/mod/hello`,
        });
    }

    /**
     * Tesztvégpont bejelentkezés ellenőrzésére
     * @returns string OK
     * @throws ApiError
     */
    public static hello(): CancelablePromise<string> {
        return __request({
            method: 'GET',
            path: `/api/hello`,
        });
    }

    /**
     * Tesztvégpont adminisztrátori jog ellenőrzésére
     * @returns string OK
     * @throws ApiError
     */
    public static helloAdmin(): CancelablePromise<string> {
        return __request({
            method: 'GET',
            path: `/api/admin/hello`,
        });
    }

}