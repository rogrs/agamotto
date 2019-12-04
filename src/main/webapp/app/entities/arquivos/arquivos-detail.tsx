import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, ICrudGetAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './arquivos.reducer';
import { IArquivos } from 'app/shared/model/arquivos.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IArquivosDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class ArquivosDetail extends React.Component<IArquivosDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { arquivosEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            <Translate contentKey="agamottoApp.arquivos.detail.title">Arquivos</Translate> [<b>{arquivosEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="nome">
                <Translate contentKey="agamottoApp.arquivos.nome">Nome</Translate>
              </span>
            </dt>
            <dd>{arquivosEntity.nome}</dd>
            <dt>
              <span id="caminho">
                <Translate contentKey="agamottoApp.arquivos.caminho">Caminho</Translate>
              </span>
            </dt>
            <dd>{arquivosEntity.caminho}</dd>
            <dt>
              <span id="md5">
                <Translate contentKey="agamottoApp.arquivos.md5">Md 5</Translate>
              </span>
            </dt>
            <dd>{arquivosEntity.md5}</dd>
            <dt>
              <span id="status">
                <Translate contentKey="agamottoApp.arquivos.status">Status</Translate>
              </span>
            </dt>
            <dd>{arquivosEntity.status}</dd>
            <dt>
              <span id="tipo">
                <Translate contentKey="agamottoApp.arquivos.tipo">Tipo</Translate>
              </span>
            </dt>
            <dd>{arquivosEntity.tipo}</dd>
            <dt>
              <span id="totalLinhas">
                <Translate contentKey="agamottoApp.arquivos.totalLinhas">Total Linhas</Translate>
              </span>
            </dt>
            <dd>{arquivosEntity.totalLinhas}</dd>
            <dt>
              <span id="criacao">
                <Translate contentKey="agamottoApp.arquivos.criacao">Criacao</Translate>
              </span>
            </dt>
            <dd>
              <TextFormat value={arquivosEntity.criacao} type="date" format={APP_LOCAL_DATE_FORMAT} />
            </dd>
            <dt>
              <span id="processamento">
                <Translate contentKey="agamottoApp.arquivos.processamento">Processamento</Translate>
              </span>
            </dt>
            <dd>
              <TextFormat value={arquivosEntity.processamento} type="date" format={APP_LOCAL_DATE_FORMAT} />
            </dd>
          </dl>
          <Button tag={Link} to="/arquivos" replace color="info">
            <FontAwesomeIcon icon="arrow-left" />{' '}
            <span className="d-none d-md-inline">
              <Translate contentKey="entity.action.back">Back</Translate>
            </span>
          </Button>
          &nbsp;
          <Button tag={Link} to={`/arquivos/${arquivosEntity.id}/edit`} replace color="primary">
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

const mapStateToProps = ({ arquivos }: IRootState) => ({
  arquivosEntity: arquivos.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(ArquivosDetail);
