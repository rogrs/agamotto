import { IPonto } from 'app/shared/model/ponto.model';
import { IColaboradores } from 'app/shared/model/colaboradores.model';

export interface IControlePonto {
  id?: number;
  data?: number;
  status?: string;
  pontos?: IPonto[];
  colaborador?: IColaboradores;
}

export const defaultValue: Readonly<IControlePonto> = {};
