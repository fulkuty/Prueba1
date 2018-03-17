import { BaseEntity } from './../../shared';

export const enum Estado {
    'CARGANDO',
    'CARGADO',
    'ESPERANDO',
    'PARADO'
}

export class ProcesoCargaMySuffix implements BaseEntity {
    constructor(
        public id?: number,
        public estado?: Estado,
        public programado?: any,
        public inicio?: any,
        public fin?: any,
        public comentario?: string,
        public zonaId?: number,
        public logs?: BaseEntity[],
    ) {
    }
}
