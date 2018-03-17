import { BaseEntity } from './../../shared';

export class ZonaCargaMySuffix implements BaseEntity {
    constructor(
        public id?: number,
        public zona?: string,
    ) {
    }
}
