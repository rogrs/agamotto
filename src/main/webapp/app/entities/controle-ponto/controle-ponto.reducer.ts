import axios from 'axios';
import { ICrudSearchAction, ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IControlePonto, defaultValue } from 'app/shared/model/controle-ponto.model';

export const ACTION_TYPES = {
  SEARCH_CONTROLEPONTOS: 'controlePonto/SEARCH_CONTROLEPONTOS',
  FETCH_CONTROLEPONTO_LIST: 'controlePonto/FETCH_CONTROLEPONTO_LIST',
  FETCH_CONTROLEPONTO: 'controlePonto/FETCH_CONTROLEPONTO',
  CREATE_CONTROLEPONTO: 'controlePonto/CREATE_CONTROLEPONTO',
  UPDATE_CONTROLEPONTO: 'controlePonto/UPDATE_CONTROLEPONTO',
  DELETE_CONTROLEPONTO: 'controlePonto/DELETE_CONTROLEPONTO',
  RESET: 'controlePonto/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IControlePonto>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false
};

export type ControlePontoState = Readonly<typeof initialState>;

// Reducer

export default (state: ControlePontoState = initialState, action): ControlePontoState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.SEARCH_CONTROLEPONTOS):
    case REQUEST(ACTION_TYPES.FETCH_CONTROLEPONTO_LIST):
    case REQUEST(ACTION_TYPES.FETCH_CONTROLEPONTO):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_CONTROLEPONTO):
    case REQUEST(ACTION_TYPES.UPDATE_CONTROLEPONTO):
    case REQUEST(ACTION_TYPES.DELETE_CONTROLEPONTO):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.SEARCH_CONTROLEPONTOS):
    case FAILURE(ACTION_TYPES.FETCH_CONTROLEPONTO_LIST):
    case FAILURE(ACTION_TYPES.FETCH_CONTROLEPONTO):
    case FAILURE(ACTION_TYPES.CREATE_CONTROLEPONTO):
    case FAILURE(ACTION_TYPES.UPDATE_CONTROLEPONTO):
    case FAILURE(ACTION_TYPES.DELETE_CONTROLEPONTO):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.SEARCH_CONTROLEPONTOS):
    case SUCCESS(ACTION_TYPES.FETCH_CONTROLEPONTO_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.FETCH_CONTROLEPONTO):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_CONTROLEPONTO):
    case SUCCESS(ACTION_TYPES.UPDATE_CONTROLEPONTO):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_CONTROLEPONTO):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: {}
      };
    case ACTION_TYPES.RESET:
      return {
        ...initialState
      };
    default:
      return state;
  }
};

const apiUrl = 'api/controle-pontos';
const apiSearchUrl = 'api/_search/controle-pontos';

// Actions

export const getSearchEntities: ICrudSearchAction<IControlePonto> = (query, page, size, sort) => ({
  type: ACTION_TYPES.SEARCH_CONTROLEPONTOS,
  payload: axios.get<IControlePonto>(`${apiSearchUrl}?query=${query}`)
});

export const getEntities: ICrudGetAllAction<IControlePonto> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_CONTROLEPONTO_LIST,
  payload: axios.get<IControlePonto>(`${apiUrl}?cacheBuster=${new Date().getTime()}`)
});

export const getEntity: ICrudGetAction<IControlePonto> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_CONTROLEPONTO,
    payload: axios.get<IControlePonto>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<IControlePonto> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_CONTROLEPONTO,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IControlePonto> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_CONTROLEPONTO,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<IControlePonto> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_CONTROLEPONTO,
    payload: axios.delete(requestUrl)
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
