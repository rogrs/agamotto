import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './linhas-arquivos.reducer';
import { ILinhasArquivos } from 'app/shared/model/linhas-arquivos.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface ILinhasArquivosDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class LinhasArquivosDetail extends React.Component<ILinhasArquivosDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { linhasArquivosEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            <Translate contentKey="agamottoApp.linhasArquivos.detail.title">LinhasArquivos</Translate> [<b>{linhasArquivosEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="nsr">
                <Translate contentKey="agamottoApp.linhasArquivos.nsr">Nsr</Translate>
              </span>
            </dt>
            <dd>{linhasArquivosEntity.nsr}</dd>
            <dt>
              <span id="tipo">
                <Translate contentKey="agamottoApp.linhasArquivos.tipo">Tipo</Translate>
              </span>
            </dt>
            <dd>{linhasArquivosEntity.tipo}</dd>
            <dt>
              <span id="dataPonto">
                <Translate contentKey="agamottoApp.linhasArquivos.dataPonto">Data Ponto</Translate>
              </span>
            </dt>
            <dd>{linhasArquivosEntity.dataPonto}</dd>
            <dt>
              <span id="horaPonto">
                <Translate contentKey="agamottoApp.linhasArquivos.horaPonto">Hora Ponto</Translate>
              </span>
            </dt>
            <dd>{linhasArquivosEntity.horaPonto}</dd>
            <dt>
              <span id="dataAjustada">
                <Translate contentKey="agamottoApp.linhasArquivos.dataAjustada">Data Ajustada</Translate>
              </span>
            </dt>
            <dd>{linhasArquivosEntity.dataAjustada}</dd>
            <dt>
              <span id="horaAjustada">
                <Translate contentKey="agamottoApp.linhasArquivos.horaAjustada">Hora Ajustada</Translate>
              </span>
            </dt>
            <dd>{linhasArquivosEntity.horaAjustada}</dd>
            <dt>
              <span id="pis">
                <Translate contentKey="agamottoApp.linhasArquivos.pis">Pis</Translate>
              </span>
            </dt>
            <dd>{linhasArquivosEntity.pis}</dd>
            <dt>
              <span id="nomeEmpregado">
                <Translate contentKey="agamottoApp.linhasArquivos.nomeEmpregado">Nome Empregado</Translate>
              </span>
            </dt>
            <dd>{linhasArquivosEntity.nomeEmpregado}</dd>
            <dt>
              <span id="linha">
                <Translate contentKey="agamottoApp.linhasArquivos.linha">Linha</Translate>
              </span>
            </dt>
            <dd>{linhasArquivosEntity.linha}</dd>
            <dt>
              <span id="tipoRegistro">
                <Translate contentKey="agamottoApp.linhasArquivos.tipoRegistro">Tipo Registro</Translate>
              </span>
            </dt>
            <dd>{linhasArquivosEntity.tipoRegistro}</dd>
            <dt>
              <span id="tipoOperacao">
                <Translate contentKey="agamottoApp.linhasArquivos.tipoOperacao">Tipo Operacao</Translate>
              </span>
            </dt>
            <dd>{linhasArquivosEntity.tipoOperacao}</dd>
            <dt>
              <Translate contentKey="agamottoApp.linhasArquivos.arquivos">Arquivos</Translate>
            </dt>
            <dd>{linhasArquivosEntity.arquivos ? linhasArquivosEntity.arquivos.nome : ''}</dd>
          </dl>
          <Button tag={Link} to="/linhas-arquivos" replace color="info">
            <FontAwesomeIcon icon="arrow-left" />{' '}
            <span className="d-none d-md-inline">
              <Translate contentKey="entity.action.back">Back</Translate>
            </span>
          </Button>
          &nbsp;
          <Button tag={Link} to={`/linhas-arquivos/${linhasArquivosEntity.id}/edit`} replace color="primary">
            <FontAwesomeIcon icon="pencil-alt" />{' '}
            <span className="d-none d-md-inline">
              <Translate contentKey="entity.action.edit">Edit</Translate>
            </span>
          </Button>
        </Col>
      </Row>
    );
  }
}

const mapStateToProps = ({ linhasArquivos }: IRootState) => ({
  linhasArquivosEntity: linhasArquivos.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(LinhasArquivosDetail);
