import React from 'react';
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

export interface ILinhasArquivosState extends IPaginationBaseState {
  search: string;
}

export class LinhasArquivos extends React.Component<ILinhasArquivosProps, ILinhasArquivosState> {
  state: ILinhasArquivosState = {
    search: '',
    ...getSortState(this.props.location, ITEMS_PER_PAGE)
  };

  componentDidMount() {
    this.reset();
  }

  componentDidUpdate() {
    if (this.props.updateSuccess) {
      this.reset();
    }
  }

  search = () => {
    if (this.state.search) {
      this.props.reset();
      this.setState({ activePage: 1 }, () => {
        const { activePage, itemsPerPage, sort, order, search } = this.state;
        this.props.getSearchEntities(search, activePage - 1, itemsPerPage, `${sort},${order}`);
      });
    }
  };

  clear = () => {
    this.props.reset();
    this.setState({ search: '', activePage: 1 }, () => {
      this.props.getEntities();
    });
  };

  handleSearch = event => this.setState({ search: event.target.value });

  reset = () => {
    this.props.reset();
    this.setState({ activePage: 1 }, () => {
      this.getEntities();
    });
  };

  handleLoadMore = () => {
    if (window.pageYOffset > 0) {
      this.setState({ activePage: this.state.activePage + 1 }, () => this.getEntities());
    }
  };

  sort = prop => () => {
    this.setState(
      {
        order: this.state.order === 'asc' ? 'desc' : 'asc',
        sort: prop
      },
      () => {
        this.reset();
      }
    );
  };

  getEntities = () => {
    const { activePage, itemsPerPage, sort, order, search } = this.state;
    if (search) {
      this.props.getSearchEntities(search, activePage - 1, itemsPerPage, `${sort},${order}`);
    } else {
      this.props.getEntities(activePage - 1, itemsPerPage, `${sort},${order}`);
    }
  };

  render() {
    const { linhasArquivosList, match } = this.props;
    return (
      <div>
        <h2 id="linhas-arquivos-heading">
          <Translate contentKey="agamottoApp.linhasArquivos.home.title">Linhas Arquivos</Translate>
          <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="agamottoApp.linhasArquivos.home.createLabel">Create a new Linhas Arquivos</Translate>
          </Link>
        </h2>
        <Row>
          <Col sm="12">
            <AvForm onSubmit={this.search}>
              <AvGroup>
                <InputGroup>
                  <AvInput
                    type="text"
                    name="search"
                    value={this.state.search}
                    onChange={this.handleSearch}
                    placeholder={translate('agamottoApp.linhasArquivos.home.search')}
                  />
                  <Button className="input-group-addon">
                    <FontAwesomeIcon icon="search" />
                  </Button>
                  <Button type="reset" className="input-group-addon" onClick={this.clear}>
                    <FontAwesomeIcon icon="trash" />
                  </Button>
                </InputGroup>
              </AvGroup>
            </AvForm>
          </Col>
        </Row>
        <div className="table-responsive">
          <InfiniteScroll
            pageStart={this.state.activePage}
            loadMore={this.handleLoadMore}
            hasMore={this.state.activePage - 1 < this.props.links.next}
            loader={<div className="loader">Loading ...</div>}
            threshold={0}
            initialLoad={false}
          >
            {linhasArquivosList && linhasArquivosList.length > 0 ? (
              <Table responsive aria-describedby="linhas-arquivos-heading">
                <thead>
                  <tr>
                    <th className="hand" onClick={this.sort('id')}>
                      <Translate contentKey="global.field.id">ID</Translate> <FontAwesomeIcon icon="sort" />
                    </th>
                    <th className="hand" onClick={this.sort('nsr')}>
                      <Translate contentKey="agamottoApp.linhasArquivos.nsr">Nsr</Translate> <FontAwesomeIcon icon="sort" />
                    </th>
                    <th className="hand" onClick={this.sort('tipo')}>
                      <Translate contentKey="agamottoApp.linhasArquivos.tipo">Tipo</Translate> <FontAwesomeIcon icon="sort" />
                    </th>
                    <th className="hand" onClick={this.sort('dataPonto')}>
                      <Translate contentKey="agamottoApp.linhasArquivos.dataPonto">Data Ponto</Translate> <FontAwesomeIcon icon="sort" />
                    </th>
                    <th className="hand" onClick={this.sort('horaPonto')}>
                      <Translate contentKey="agamottoApp.linhasArquivos.horaPonto">Hora Ponto</Translate> <FontAwesomeIcon icon="sort" />
                    </th>
                    <th className="hand" onClick={this.sort('dataAjustada')}>
                      <Translate contentKey="agamottoApp.linhasArquivos.dataAjustada">Data Ajustada</Translate>{' '}
                      <FontAwesomeIcon icon="sort" />
                    </th>
                    <th className="hand" onClick={this.sort('horaAjustada')}>
                      <Translate contentKey="agamottoApp.linhasArquivos.horaAjustada">Hora Ajustada</Translate>{' '}
                      <FontAwesomeIcon icon="sort" />
                    </th>
                    <th className="hand" onClick={this.sort('pis')}>
                      <Translate contentKey="agamottoApp.linhasArquivos.pis">Pis</Translate> <FontAwesomeIcon icon="sort" />
                    </th>
                    <th className="hand" onClick={this.sort('nomeEmpregado')}>
                      <Translate contentKey="agamottoApp.linhasArquivos.nomeEmpregado">Nome Empregado</Translate>{' '}
                      <FontAwesomeIcon icon="sort" />
                    </th>
                    <th className="hand" onClick={this.sort('linha')}>
                      <Translate contentKey="agamottoApp.linhasArquivos.linha">Linha</Translate> <FontAwesomeIcon icon="sort" />
                    </th>
                    <th className="hand" onClick={this.sort('tipoRegistro')}>
                      <Translate contentKey="agamottoApp.linhasArquivos.tipoRegistro">Tipo Registro</Translate>{' '}
                      <FontAwesomeIcon icon="sort" />
                    </th>
                    <th className="hand" onClick={this.sort('tipoOperacao')}>
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
              <div className="alert alert-warning">
                <Translate contentKey="agamottoApp.linhasArquivos.home.notFound">No Linhas Arquivos found</Translate>
              </div>
            )}
          </InfiniteScroll>
        </div>
      </div>
    );
  }
}

const mapStateToProps = ({ linhasArquivos }: IRootState) => ({
  linhasArquivosList: linhasArquivos.entities,
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

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(LinhasArquivos);
