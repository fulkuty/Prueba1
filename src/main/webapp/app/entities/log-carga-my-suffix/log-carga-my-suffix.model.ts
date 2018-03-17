import { BaseEntity } from './../../shared';

export const enum Estado {
    'CARGANDO',
    'CARGADO',
    'ESPERANDO',
    'PARADO'
}

export class LogCargaMySuffix implements BaseEntity {
    constructor(
        public id?: number,
        public time?: any,
        public estado?: Estado,
        public programado?: any,
        public inicio?: any,
        public fin?: any,
        public comentario?: string,
        public zonaId?: number,
        public procesoId?: number,
    ) {
    }
}
