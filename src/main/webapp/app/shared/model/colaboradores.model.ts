import { Moment } from 'moment';
import { ICargos } from 'app/shared/model/cargos.model';
import { IControlePonto } from 'app/shared/model/controle-ponto.model';
import { TipoSexo } from 'app/shared/model/enumerations/tipo-sexo.model';

export interface IColaboradores {
  id?: number;
  nome?: string;
  matricula?: string;
  pis?: string;
  sexo?: TipoSexo;
  admissao?: Moment;
  cpf?: string;
  ci?: string;
  demissao?: Moment;
  atualizado?: Moment;
  criacao?: Moment;
  cargos?: ICargos[];
  controlePontos?: IControlePonto[];
}

export const defaultValue: Readonly<IColaboradores> = {};
