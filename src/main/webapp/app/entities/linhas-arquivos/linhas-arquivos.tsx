import React, { useState, useEffect } from 'react';
import InfiniteScroll from 'react-infinite-scroller';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, InputGroup, Col, Row, Table } from 'reactstrap';
import { AvForm, AvGroup, AvInput } from 'availity-reactstrap-validation';
import { Translate, translate, ICrudSearchAction, ICrudGetAllAction, getSortState, IPaginationBaseState } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getSearchEntities, getEntities, reset } from './linhas-arquivos.reducer';
import { ILinhasArquivos } from 'app/shared/model/linhas-arquivos.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { ITEMS_PER_PAGE } from 'app/shared/util/pagination.constants';

export interface ILinhasArquivosProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export const LinhasArquivos = (props: ILinhasArquivosProps) => {
  const [search, setSearch] = useState('');
  const [paginationState, setPaginationState] = useState(getSortState(props.location, ITEMS_PER_PAGE));
  const [sorting, setSorting] = useState(false);

  const getAllEntities = () => {
    if (search) {
      props.getSearchEntities(
        search,
        paginationState.activePage - 1,
        paginationState.itemsPerPage,
        `${paginationState.sort},${paginationState.order}`
      );
    } else {
      props.getEntities(paginationState.activePage - 1, paginationState.itemsPerPage, `${paginationState.sort},${paginationState.order}`);
    }
  };

  const resetAll = () => {
    props.reset();
    setPaginationState({
      ...paginationState,
      activePage: 1
    });
  };

  useEffect(() => {
    resetAll();
  }, []);

  const startSearching = () => {
    if (search) {
      props.reset();
      setPaginationState({
        ...paginationState,
        activePage: 1
      });
      props.getSearchEntities(
        search,
        paginationState.activePage - 1,
        paginationState.itemsPerPage,
        `${paginationState.sort},${paginationState.order}`
      );
    }
  };

  const clear = () => {
    props.reset();
    setSearch('');
    setPaginationState({
      ...paginationState,
      activePage: 1
    });
    props.getEntities();
  };

  const handleSearch = event => setSearch(event.target.value);

  useEffect(() => {
    getAllEntities();
  }, [paginationState.activePage]);

  const handleLoadMore = () => {
    if (window.pageYOffset > 0) {
      setPaginationState({
        ...paginationState,
        activePage: paginationState.activePage + 1
      });
    }
  };

  useEffect(() => {
    if (sorting) {
      getAllEntities();
      setSorting(false);
    }
  }, [sorting, search]);

  const sort = p => () => {
    props.reset();
    setPaginationState({
      ...paginationState,
      activePage: 1,
      order: paginationState.order === 'asc' ? 'desc' : 'asc',
      sort: p
    });
    setSorting(true);
  };

  const { linhasArquivosList, match, loading } = props;
  return (
    <div>
      <h2 id="linhas-arquivos-heading">
        <Translate contentKey="agamottoApp.linhasArquivos.home.title">Linhas Arquivos</Translate>
        <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
          <FontAwesomeIcon icon="plus" />
          &nbsp;
          <Translate contentKey="agamottoApp.linhasArquivos.home.createLabel">Create new Linhas Arquivos</Translate>
        </Link>
      </h2>
      <Row>
        <Col sm="12">
          <AvForm onSubmit={startSearching}>
            <AvGroup>
              <InputGroup>
                <AvInput
                  type="text"
                  name="search"
                  value={search}
                  onChange={handleSearch}
                  placeholder={translate('agamottoApp.linhasArquivos.home.search')}
                />
                <Button className="input-group-addon">
                  <FontAwesomeIcon icon="search" />
                </Button>
                <Button type="reset" className="input-group-addon" onClick={clear}>
                  <FontAwesomeIcon icon="trash" />
                </Button>
              </InputGroup>
            </AvGroup>
          </AvForm>
        </Col>
      </Row>
      <div className="table-responsive">
        <InfiniteScroll
          pageStart={paginationState.activePage}
          loadMore={handleLoadMore}
          hasMore={paginationState.activePage - 1 < props.links.next}
          loader={<div className="loader">Loading ...</div>}
          threshold={0}
          initialLoad={false}
        >
          {linhasArquivosList && linhasArquivosList.length > 0 ? (
            <Table responsive>
              <thead>
                <tr>
                  <th className="hand" onClick={sort('id')}>
                    <Translate contentKey="global.field.id">ID</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('nsr')}>
                    <Translate contentKey="agamottoApp.linhasArquivos.nsr">Nsr</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('tipo')}>
                    <Translate contentKey="agamottoApp.linhasArquivos.tipo">Tipo</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('dataPonto')}>
                    <Translate contentKey="agamottoApp.linhasArquivos.dataPonto">Data Ponto</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('horaPonto')}>
                    <Translate contentKey="agamottoApp.linhasArquivos.horaPonto">Hora Ponto</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('dataAjustada')}>
                    <Translate contentKey="agamottoApp.linhasArquivos.dataAjustada">Data Ajustada</Translate>{' '}
                    <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('horaAjustada')}>
                    <Translate contentKey="agamottoApp.linhasArquivos.horaAjustada">Hora Ajustada</Translate>{' '}
                    <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('pis')}>
                    <Translate contentKey="agamottoApp.linhasArquivos.pis">Pis</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('nomeEmpregado')}>
                    <Translate contentKey="agamottoApp.linhasArquivos.nomeEmpregado">Nome Empregado</Translate>{' '}
                    <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('linha')}>
                    <Translate contentKey="agamottoApp.linhasArquivos.linha">Linha</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('tipoRegistro')}>
                    <Translate contentKey="agamottoApp.linhasArquivos.tipoRegistro">Tipo Registro</Translate>{' '}
                    <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('tipoOperacao')}>
                    <Translate contentKey="agamottoApp.linhasArquivos.tipoOperacao">Tipo Operacao</Translate>{' '}
                    <FontAwesomeIcon icon="sort" />
                  </th>
                  <th>
                    <Translate contentKey="agamottoApp.linhasArquivos.arquivos">Arquivos</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th />
                </tr>
              </thead>
              <tbody>
                {linhasArquivosList.map((linhasArquivos, i) => (
                  <tr key={`entity-${i}`}>
                    <td>
                      <Button tag={Link} to={`${match.url}/${linhasArquivos.id}`} color="link" size="sm">
                        {linhasArquivos.id}
                      </Button>
                    </td>
                    <td>{linhasArquivos.nsr}</td>
                    <td>{linhasArquivos.tipo}</td>
                    <td>{linhasArquivos.dataPonto}</td>
                    <td>{linhasArquivos.horaPonto}</td>
                    <td>{linhasArquivos.dataAjustada}</td>
                    <td>{linhasArquivos.horaAjustada}</td>
                    <td>{linhasArquivos.pis}</td>
                    <td>{linhasArquivos.nomeEmpregado}</td>
                    <td>{linhasArquivos.linha}</td>
                    <td>
                      <Translate contentKey={`agamottoApp.TipoRegistro.${linhasArquivos.tipoRegistro}`} />
                    </td>
                    <td>
                      <Translate contentKey={`agamottoApp.TipoOperacao.${linhasArquivos.tipoOperacao}`} />
                    </td>
                    <td>
                      {linhasArquivos.arquivos ? (
                        <Link to={`arquivos/${linhasArquivos.arquivos.id}`}>{linhasArquivos.arquivos.nome}</Link>
                      ) : (
                        ''
                      )}
                    </td>
                    <td className="text-right">
                      <div className="btn-group flex-btn-group-container">
                        <Button tag={Link} to={`${match.url}/${linhasArquivos.id}`} color="info" size="sm">
                          <FontAwesomeIcon icon="eye" />{' '}
                          <span className="d-none d-md-inline">
                            <Translate contentKey="entity.action.view">View</Translate>
                          </span>
                        </Button>
                        <Button tag={Link} to={`${match.url}/${linhasArquivos.id}/edit`} color="primary" size="sm">
                          <FontAwesomeIcon icon="pencil-alt" />{' '}
                          <span className="d-none d-md-inline">
                            <Translate contentKey="entity.action.edit">Edit</Translate>
                          </span>
                        </Button>
                        <Button tag={Link} to={`${match.url}/${linhasArquivos.id}/delete`} color="danger" size="sm">
                          <FontAwesomeIcon icon="trash" />{' '}
                          <span className="d-none d-md-inline">
                            <Translate contentKey="entity.action.delete">Delete</Translate>
                          </span>
                        </Button>
                      </div>
                    </td>
                  </tr>
                ))}
              </tbody>
            </Table>
          ) : (
            !loading && (
              <div className="alert alert-warning">
                <Translate contentKey="agamottoApp.linhasArquivos.home.notFound">No Linhas Arquivos found</Translate>
              </div>
            )
          )}
        </InfiniteScroll>
      </div>
    </div>
  );
};

const mapStateToProps = ({ linhasArquivos }: IRootState) => ({
  linhasArquivosList: linhasArquivos.entities,
  loading: linhasArquivos.loading,
  totalItems: linhasArquivos.totalItems,
  links: linhasArquivos.links,
  entity: linhasArquivos.entity,
  updateSuccess: linhasArquivos.updateSuccess
});

const mapDispatchToProps = {
  getSearchEntities,
  getEntities,
  reset
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(LinhasArquivos);
