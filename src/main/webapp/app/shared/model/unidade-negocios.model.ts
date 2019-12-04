import { IFeriados } from 'app/shared/model/feriados.model';
import { IDepartamentos } from 'app/shared/model/departamentos.model';
import { TipoBoolean } from 'app/shared/model/enumerations/tipo-boolean.model';

export interface IUnidadeNegocios {
  id?: number;
  razaoSocial?: string;
  nomeEmpresa?: string;
  cnpj?: string;
  inscricaoEstadual?: string;
  empregadora?: TipoBoolean;
  feriados?: IFeriados[];
  departamentos?: IDepartamentos[];
}

export const defaultValue: Readonly<IUnidadeNegocios> = {};
