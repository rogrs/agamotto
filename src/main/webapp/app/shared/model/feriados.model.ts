import { Moment } from 'moment';
import { IUnidadeNegocios } from 'app/shared/model/unidade-negocios.model';

export interface IFeriados {
  id?: number;
  nome?: string;
  fixoOuMudaTodoAno?: boolean;
  dataFeriado?: Moment;
  unidadeNegocios?: IUnidadeNegocios;
}

export const defaultValue: Readonly<IFeriados> = {
  fixoOuMudaTodoAno: false
};
