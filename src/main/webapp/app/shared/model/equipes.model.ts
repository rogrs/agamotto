import { IDepartamentos } from 'app/shared/model/departamentos.model';

export interface IEquipes {
  id?: number;
  nome?: string;
  departamentos?: IDepartamentos;
}

export const defaultValue: Readonly<IEquipes> = {};
