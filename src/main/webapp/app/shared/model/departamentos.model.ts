import { IEquipes } from 'app/shared/model/equipes.model';
import { IUnidadeNegocios } from 'app/shared/model/unidade-negocios.model';

export interface IDepartamentos {
  id?: number;
  nome?: string;
  equipes?: IEquipes[];
  unidadeNegocios?: IUnidadeNegocios;
}

export const defaultValue: Readonly<IDepartamentos> = {};
