/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */

import type { ImageDto } from './ImageDto';
import type { Pageable } from './Pageable';
import type { Sort } from './Sort';

export type PageImageDto = {
    totalPages?: number;
    totalElements?: number;
    number?: number;
    size?: number;
    numberOfElements?: number;
    content?: Array<ImageDto>;
    sort?: Sort;
    first?: boolean;
    last?: boolean;
    pageable?: Pageable;
    empty?: boolean;
}