import { Moment } from 'moment';
import { ILinhasArquivos } from 'app/shared/model/linhas-arquivos.model';
import { StatusArquivo } from 'app/shared/model/enumerations/status-arquivo.model';

export interface IArquivos {
  id?: number;
  nome?: string;
  caminho?: string;
  md5?: string;
  status?: StatusArquivo;
  tipo?: string;
  totalLinhas?: number;
  criacao?: Moment;
  processamento?: Moment;
  linhas?: ILinhasArquivos[];
}

export const defaultValue: Readonly<IArquivos> = {};
