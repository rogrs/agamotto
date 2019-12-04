import { TipoBoolean } from 'app/shared/model/enumerations/tipo-boolean.model';

export interface ITurnos {
  id?: number;
  descricaoTurno?: string;
  intervaloFlexivel?: TipoBoolean;
}

export const defaultValue: Readonly<ITurnos> = {};
