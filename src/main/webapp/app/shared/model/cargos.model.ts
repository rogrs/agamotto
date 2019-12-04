import { IColaboradores } from 'app/shared/model/colaboradores.model';

export interface ICargos {
  id?: number;
  nome?: string;
  colaborador?: IColaboradores;
}

export const defaultValue: Readonly<ICargos> = {};
